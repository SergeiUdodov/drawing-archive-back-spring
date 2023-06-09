package com.petproject.archive.dao;

import com.petproject.archive.entity.Drawing;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

@Repository
public class DrawingDaoImpl implements DrawingDao{

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Drawing> findAllDrawings() {

        Session currentSession = entityManager.unwrap(Session.class);
        Query<Drawing> theQuery = currentSession.createQuery("from Drawing", Drawing.class);
        List<Drawing> drawings = theQuery.getResultList();

        return drawings;
    }

    @Override
    public void addDrawing(Drawing newDrawing) {

        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.persist(newDrawing);
    }

    @Override
    public void updateDrawing(Drawing currentDrawing) {

        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(currentDrawing);
    }

    @Override
    public void deleteDrawing(long drawingId) {

        Session currentSession = entityManager.unwrap(Session.class);

        Query theQuery = currentSession.createQuery("delete from Drawing where id=:DrawingId");
        theQuery.setParameter("DrawingId", drawingId);

        theQuery.executeUpdate();
    }

    @Override
    public Drawing findDrawingById(long drawingId) {

        Session currentSession = entityManager.unwrap(Session.class);
        Drawing theDrawing = currentSession.get(Drawing.class, drawingId);
        return theDrawing;
    }
}
