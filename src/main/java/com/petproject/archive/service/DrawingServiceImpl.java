package com.petproject.archive.service;

import com.petproject.archive.dao.DrawingDao;
import com.petproject.archive.entity.Drawing;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrawingServiceImpl implements DrawingService{
    @Autowired
    private DrawingDao drawingDao;
    @Override
    @Transactional
    public List<Drawing> findAllDrawings(HttpServletRequest request) {
        return drawingDao.findAllDrawings();
    }
}
