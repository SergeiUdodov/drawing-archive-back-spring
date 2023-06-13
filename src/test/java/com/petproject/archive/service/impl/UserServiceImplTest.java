package com.petproject.archive.service.impl;

import com.petproject.archive.config.JwtTokenUtil;
import com.petproject.archive.dao.UserDao;
import com.petproject.archive.entity.Role;
import com.petproject.archive.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private HttpServletRequest request1;

    @Mock
    private HttpServletRequest request2;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Test
    void testFindAllUsers(){

        User user = getSimpleUser();

        Mockito.when(request1.getHeader("Authorization")).thenReturn("Bearer 1");
        Mockito.when(request2.getHeader("Authorization")).thenReturn(null);
        Mockito.when(jwtTokenUtil.getUserEmailFromToken("1")).thenReturn("1@mail.ru");
        Mockito.when(userDao.findUserByEmail("1@mail.ru")).thenReturn(getSimpleUser());
        Mockito.when(userDao.findUserByEmail(null)).thenReturn(null);

        User result1 = userServiceImpl.getUserByToken(request1);
        User result2 = userServiceImpl.getUserByToken(request2);

        Assertions.assertNotNull(result1);
        Assertions.assertEquals(user, result1);
        Assertions.assertNull(result2);

    }

    @Test
    void testIsUserAdmin(){

        Mockito.when(request1.getHeader(any())).thenReturn("Bearer 1");
        Mockito.when(request2.getHeader(any())).thenReturn("Bearer 2");
        Mockito.when(jwtTokenUtil.getUserEmailFromToken("1")).thenReturn("email1");
        Mockito.when(jwtTokenUtil.getUserEmailFromToken("2")).thenReturn("email2");
        Mockito.when(userDao.findUserByEmail("email1")).thenReturn(getSimpleUser());
        Mockito.when(userDao.findUserByEmail("email2")).thenReturn(getAdminUser());

        boolean result1 = userServiceImpl.isUserAdmin(request1);
        boolean result2 = userServiceImpl.isUserAdmin(request2);

        Assertions.assertEquals(false, result1);
        Assertions.assertEquals(true, result2);
    }

    private User getSimpleUser() {

        User simpleUser = new User();
        simpleUser.setRoles(List.of(new Role("ROLE_USER")));

        return simpleUser;
    }

    private User getAdminUser() {

        User adminUser = new User();
        adminUser.setRoles(List.of(new Role("ROLE_USER"), new Role("ROLE_ADMIN")));

        return adminUser;
    }
}
