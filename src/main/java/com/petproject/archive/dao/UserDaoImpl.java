package com.petproject.archive.dao;

import com.petproject.archive.entity.Drawing;
import com.petproject.archive.entity.Role;
import com.petproject.archive.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<User> findAllUsers() {

        TypedQuery<User> theQuery = entityManager.createQuery("from User", User.class);
        List<User> users = theQuery.getResultList();
        return users;
    }

    @Override
    public User findUserByEmail(String userEmail) {

        TypedQuery<User> theQuery = entityManager.createQuery("from User where email=:uEmail", User.class);
        theQuery.setParameter("uEmail", userEmail);
        User theUser = null;
        try {
            theUser = theQuery.getSingleResult();
        } catch (Exception e) {
            theUser = null;
        }

        return theUser;
    }

    @Override
    public void save(User theUser) {

        entityManager.persist(theUser);
    }

    @Override
    public void updateUser(User theUser) {

        entityManager.merge(theUser);
    }

    @Override
    public Role findRoleByName(String theRoleName) {

        TypedQuery<Role> theQuery = entityManager.createQuery("from Role where name=:roleName", Role.class);
        theQuery.setParameter("roleName", theRoleName);

        Role theRole = null;

        try {
            theRole = theQuery.getSingleResult();
        } catch (Exception e) {
            theRole = null;
        }

        return theRole;
    }
}
