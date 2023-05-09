package com.petproject.archive.dao;

import com.petproject.archive.entity.User;
import com.petproject.archive.entity.Role;

import java.util.List;

public interface UserDao {

    public User findUserByEmail(String userEmail);

    public void save(User theUser);

    public void updateUser(User theUser);

    public Role findRoleByName(String theRoleName);

    public List<User> findAllUsers();
}
