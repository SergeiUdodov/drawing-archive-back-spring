//package com.petproject.archive.service;
//
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.petproject.archive.dao.UserDao;
//
//@Service
//public class JwtUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserDao userDao;
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
//
//        com.petproject.archive.entity.User user = userDao.findUserByEmail(userEmail);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with email: " + userEmail);
//        }
//
//        return new User(user.getEmail(), user.getPassword(), user.getRoles());
//    }
//}
