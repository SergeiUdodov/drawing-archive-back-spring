package com.petproject.archive.service;

import com.petproject.archive.entity.Drawing;
import com.petproject.archive.entity.User;
import com.petproject.archive.model.CrmRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {

    public User getUserByToken(HttpServletRequest request);
    public boolean isUserAdmin(HttpServletRequest request);
    public List<User> findAllUsers(String pathVariable);

}
