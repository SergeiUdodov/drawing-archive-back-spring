package com.petproject.archive.service;

import com.petproject.archive.dao.DrawingDao;
import com.petproject.archive.entity.Drawing;
import com.petproject.archive.entity.User;
import com.petproject.archive.model.CrmDrawing;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class DrawingServiceImpl implements DrawingService{

    private int userCount = 100;

    @Autowired
    private DrawingDao drawingDao;

    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Override
    @Transactional
    public List<Drawing> findAllDrawings(HttpServletRequest request) {
        return drawingDao.findAllDrawings();
    }

    @Override
    @Transactional
    public Drawing addDrawing(CrmDrawing crmDrawing) {

        Drawing newDrawing = new Drawing();

        newDrawing.setDesignation(crmDrawing.getDesignation());
        newDrawing.setName(crmDrawing.getName());
        newDrawing.setImageURL(crmDrawing.getImageURL());
        newDrawing.setVersion(1);

        Date current = new Date();
        String currentDate = formatter.format(current);
        newDrawing.setDate(currentDate);

        //until security not added
        userCount++;
        newDrawing.setUser(new User("UserEmail№" + userCount, "FirstName№" + userCount,
                "LastName№" + userCount, "123"));

        drawingDao.addDrawing(newDrawing);

        //for testing
        return newDrawing;
    }

    @Override
    @Transactional
    public Drawing updateDrawing(long drawingId, CrmDrawing crmDrawing) {

        Drawing currentDrawing = findDrawingById(drawingId);

        if (currentDrawing == null) {
            throw new RuntimeException("Drawing id not found - " + drawingId);
        }

        currentDrawing.setDesignation(crmDrawing.getDesignation());
        currentDrawing.setName(crmDrawing.getName());
        currentDrawing.setImageURL(crmDrawing.getImageURL());

        long version = currentDrawing.getVersion();
        version++;
        currentDrawing.setVersion(version);

        Date current = new Date();
        String currentDate = formatter.format(current);
        currentDrawing.setDate(currentDate);

        drawingDao.updateDrawing(currentDrawing);

        //for testing
        return currentDrawing;
    }

    @Override
    @Transactional
    public void deleteDrawing(long drawingId) {

        Drawing tempDrawing = findDrawingById(drawingId);

        if (tempDrawing == null) {
            throw new RuntimeException("Drawing id not found - " + drawingId);
        }

        drawingDao.deleteDrawing(drawingId);
    }

    @Override
    @Transactional
    public Drawing findDrawingById(long drawingId){

        return drawingDao.findDrawingById(drawingId);
    }
}
