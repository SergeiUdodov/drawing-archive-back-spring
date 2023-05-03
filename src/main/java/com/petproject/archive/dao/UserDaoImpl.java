//package com.petproject.archive.dao;
//
//import com.petproject.archive.entity.User;
//import jakarta.persistence.EntityManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//import org.hibernate.Session;
//import org.hibernate.query.Query;
//
//@Repository
//public class UserDaoImpl implements UserDao {
//
//    @Autowired
//    private EntityManager entityManager;
//    @Override
//    public User findUserByEmail(String userEmail) {
//
//        Session currentSession = entityManager.unwrap(Session.class);
//
//        Query<User> theQuery = currentSession.createQuery("from User where email=:uEmail", User.class);
//        theQuery.setParameter("uEmail", userEmail);
//        User theUser = null;
//        try {
//            theUser = theQuery.getSingleResult();
//        } catch (Exception e) {
//            theUser = null;
//        }
//
//        return theUser;
//    }
//}
