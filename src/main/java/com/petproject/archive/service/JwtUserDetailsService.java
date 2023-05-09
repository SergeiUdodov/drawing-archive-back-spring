package com.petproject.archive.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.petproject.archive.model.CrmUser;
import java.util.Arrays;

import com.petproject.archive.dao.UserDao;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        com.petproject.archive.entity.User user = userDao.findUserByEmail(userEmail);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + userEmail);
        }

        return new User(user.getEmail(), user.getPassword(), user.getRoles());
    }

    @Transactional
    public com.petproject.archive.entity.User save(CrmUser user) {
        com.petproject.archive.entity.User newUser = new com.petproject.archive.entity.User();
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        newUser.setRoles(Arrays.asList(userDao.findRoleByName("ROLE_USER")));
        userDao.save(newUser);
        return newUser;
    }

    @Transactional
    public com.petproject.archive.entity.User updateUser(CrmUser crmUser, HttpServletRequest request) {

        com.petproject.archive.entity.User theUser = userService.getUserByToken(request);

        theUser.setEmail(crmUser.getEmail());
        theUser.setFirstName(crmUser.getFirstName());
        theUser.setLastName(crmUser.getLastName());
        if(!"".equals(crmUser.getPassword())) {
            theUser.setPassword(bcryptEncoder.encode(crmUser.getPassword()));
        }

        userDao.updateUser(theUser);
        return theUser;
    }

}
