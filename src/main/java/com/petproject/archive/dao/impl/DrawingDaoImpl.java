package com.petproject.archive.dao.impl;

import com.petproject.archive.dao.DrawingDao;
import com.petproject.archive.entity.Drawing;
import com.petproject.archive.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@Repository
public class DrawingDaoImpl implements DrawingDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Drawing> findAllDrawings() {


        TypedQuery<Drawing> theQuery = entityManager.createQuery("from Drawing", Drawing.class);
        List<Drawing> drawings = theQuery.getResultList();

        return drawings;
    }

    @Override
    public void addDrawing(Drawing newDrawing) {

        entityManager.persist(newDrawing);
    }

    @Override
    public void updateDrawing(Drawing currentDrawing) {

        entityManager.merge(currentDrawing);
    }

    @Override
    public void deleteDrawing(long drawingId) {

        jakarta.persistence.Query theQuery = entityManager.createQuery("delete from Drawing where id=:DrawingId");
        theQuery.setParameter("DrawingId", drawingId);
        theQuery.executeUpdate();
    }

    @Override
    public Drawing findDrawingById(long drawingId) {

        return entityManager.find(Drawing.class, drawingId);
    }

    @Override
    public Drawing findDrawingByDesignation(String designation) {

        TypedQuery<Drawing> theQuery = entityManager.createQuery("from Drawing where designation=:dDesignation", Drawing.class);
        theQuery.setParameter("dDesignation", designation);
        Drawing theDrawing = null;
        try {
            theDrawing = theQuery.getSingleResult();
        } catch (Exception e) {
            theDrawing = null;
        }

        return theDrawing;
    }
}
