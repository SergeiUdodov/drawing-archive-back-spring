package com.petproject.archive.service;

import com.petproject.archive.config.JwtTokenUtil;
import com.petproject.archive.dao.UserDao;
import com.petproject.archive.entity.Drawing;
import com.petproject.archive.entity.User;
import com.petproject.archive.entity.Role;
import com.petproject.archive.model.CrmRequest;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public List<User> findAllUsers(String pathVariable) {

        List<User> users = userDao.findAllUsers();

        //filter users by pathVariable
        users = users.stream().filter(user -> user.getEmail().contains(pathVariable)).collect(Collectors.toList());

        //sort users by email
        Comparator<User> byEmail = (first, second) -> {
            return first.getEmail().compareToIgnoreCase(second.getEmail());
        };

        users.sort(byEmail);

        return users;
    }

    @Override
    @Transactional
    public User getUserByToken(HttpServletRequest request) {

        final String requestTokenHeader = request.getHeader("Authorization");

        String userEmail = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                userEmail = jwtTokenUtil.getUserEmailFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token is null or does not begin with Bearer String");
        }

        return userDao.findUserByEmail(userEmail);
    }

    @Override
    @Transactional
    public boolean isUserAdmin(HttpServletRequest request) {

        boolean isAdmin = false;
        List<Role> userRoles = getUserByToken(request).getRoles();
        for (Role role : userRoles) {
            if ("ROLE_ADMIN".equals(role.getName())) {
                isAdmin = true;
            }
        }
        return isAdmin;
    }
}
