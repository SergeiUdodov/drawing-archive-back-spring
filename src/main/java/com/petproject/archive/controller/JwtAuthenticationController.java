package com.petproject.archive.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.petproject.archive.config.JwtTokenUtil;
import com.petproject.archive.service.JwtUserDetailsService;
import org.springframework.http.ResponseEntity;
import com.petproject.archive.model.JwtRequest;
import com.petproject.archive.model.JwtResponse;
import org.springframework.security.core.userdetails.UserDetails;
import com.petproject.archive.model.CrmUser;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.BadCredentialsException;


@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody CrmUser user) throws Exception {
        return ResponseEntity.ok(jwtUserDetailsService.save(user));
    }

    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody CrmUser crmUser, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(jwtUserDetailsService.updateUser(crmUser, request));
    }

    private void authenticate(String userEmail, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
