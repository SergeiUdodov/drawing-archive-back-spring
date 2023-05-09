package com.petproject.archive.service;

import com.petproject.archive.dao.DrawingDao;
import com.petproject.archive.entity.Drawing;
import com.petproject.archive.entity.User;
import com.petproject.archive.model.CrmDrawing;
import com.petproject.archive.model.CrmRequest;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrawingServiceImpl implements DrawingService{

    @Autowired
    private DrawingDao drawingDao;

    @Autowired
    private UserService userService;

    private SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    @Override
    @Transactional
    public List<Drawing> findAllDrawings(CrmRequest crmRequest) {

        String requestText = crmRequest.getText();
        List<Drawing> drawings = drawingDao.findAllDrawings();

        //filter drawings by request text
        drawings = drawings.stream().filter(drawing -> drawing.getDesignation().contains(requestText)).collect(Collectors.toList());

        //sort drawings by designation
        Comparator<Drawing> byDesignation = (first, second) -> {
            return first.getDesignation().compareToIgnoreCase(second.getDesignation());
        };

        drawings.sort(byDesignation);

        return drawings;
    }

    @Override
    @Transactional
    public Drawing addDrawing(CrmDrawing crmDrawing, HttpServletRequest request) {

        Drawing newDrawing = new Drawing();

        newDrawing.setDesignation(crmDrawing.getDesignation());
        newDrawing.setName(crmDrawing.getName());
        newDrawing.setImageURL(crmDrawing.getImageURL());
        newDrawing.setVersion(1);

        Date current = new Date();
        String currentDate = formatter.format(current);
        newDrawing.setDate(currentDate);

        User theUser = userService.getUserByToken(request);

        if (theUser == null) {
            throw new RuntimeException("User not found");
        }

        newDrawing.setUser(theUser);

        drawingDao.addDrawing(newDrawing);

        //for testing
        return newDrawing;
    }

    @Override
    @Transactional
    public Drawing updateDrawing(long drawingId, CrmDrawing crmDrawing, HttpServletRequest request) {

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

        User theUser = userService.getUserByToken(request);

        if (theUser == null) {
            throw new RuntimeException("User not found");
        }

        currentDrawing.setUser(theUser);

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
