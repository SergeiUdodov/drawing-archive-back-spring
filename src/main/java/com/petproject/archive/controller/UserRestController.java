package com.petproject.archive.controller;

import com.petproject.archive.entity.Drawing;
import com.petproject.archive.model.CrmRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.petproject.archive.entity.User;
import com.petproject.archive.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/userByToken")
    public User getUserByToken(HttpServletRequest request) {

        return userService.getUserByToken(request);
    }

    @GetMapping("/isUserAdmin")
    public boolean isUserAdmin(HttpServletRequest request) {

        return userService.isUserAdmin(request);
    }
}
