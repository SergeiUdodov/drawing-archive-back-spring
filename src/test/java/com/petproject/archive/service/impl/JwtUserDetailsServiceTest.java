package com.petproject.archive.service.impl;

import com.petproject.archive.dao.UserDao;
import com.petproject.archive.entity.Role;
import com.petproject.archive.model.CrmUser;
import com.petproject.archive.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class JwtUserDetailsServiceTest {

    @Mock
    private UserDao userDao;

    @Mock
    private PasswordEncoder bcryptEncoder;

    @Mock
    private UserService userService;

    @InjectMocks
    private JwtUserDetailsService jwtUserDetailsService;

    @Test
    void testLoadUserByUsername(){
        CrmUser crmUser = getCrmUser();
        com.petproject.archive.entity.User user = getUser(crmUser);
        UserDetails userDetails = new User(user.getEmail(), user.getPassword(), user.getRoles());

        Mockito.when(userDao.findUserByEmail("email1")).thenReturn(getUser(getCrmUser()));
        Mockito.when(userDao.findUserByEmail("email2")).thenReturn(null);

        UserDetails result = jwtUserDetailsService.loadUserByUsername("email1");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(userDetails, result);
        Assertions.assertThrows(UsernameNotFoundException.class, ()->{jwtUserDetailsService.loadUserByUsername("email2");});
    }

    @Test
    void testSave(){
        CrmUser crmUser = getCrmUser();
        com.petproject.archive.entity.User user = getUser(crmUser);

        Mockito.when(bcryptEncoder.encode(crmUser.getPassword())).thenReturn(crmUser.getPassword());
        Mockito.when(userDao.findRoleByName("ROLE_USER")).thenReturn(new Role("ROLE_USER"));

        com.petproject.archive.entity.User result = jwtUserDetailsService.save(getCrmUser());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result);
    }

    @Test
    void testUpdateUser(){
        CrmUser crmUser = getCrmUser();
        com.petproject.archive.entity.User user = getUser(crmUser);
        com.petproject.archive.entity.User test = new com.petproject.archive.entity.User();
        test.setRoles(Arrays.asList(new Role("ROLE_USER")));

        Mockito.when(bcryptEncoder.encode(crmUser.getPassword())).thenReturn(crmUser.getPassword());
        Mockito.when(userService.getUserByToken(any())).thenReturn(test);

        com.petproject.archive.entity.User result = jwtUserDetailsService.updateUser(getCrmUser(), null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(user, result);
    }

    private com.petproject.archive.entity.User getUser(CrmUser crmUser){

        com.petproject.archive.entity.User user = new com.petproject.archive.entity.User();
        user.setEmail(crmUser.getEmail());
        user.setPassword(crmUser.getPassword());
        user.setRoles(List.of(new Role("ROLE_USER")));

        return user;
    }

    private CrmUser getCrmUser() {

        CrmUser crmUser = new CrmUser();
        crmUser.setEmail("test@mail.com");
        crmUser.setPassword("testPassword");

        return crmUser;
    }

}
