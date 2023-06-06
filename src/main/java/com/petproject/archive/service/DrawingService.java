package com.petproject.archive.service;

import com.petproject.archive.entity.Drawing;
import com.petproject.archive.model.CrmDrawing;
import com.petproject.archive.model.CrmRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface DrawingService {
    public List<Drawing> findAllDrawings();
    public List<Drawing> findAllDrawingsByRequest(String pathVariable);
    public Drawing addDrawing(CrmDrawing theDrawing, HttpServletRequest request);

    public Drawing updateDrawing(long drawingId, CrmDrawing crmDrawing, HttpServletRequest request);

    public void deleteDrawing(long drawingId);

    public Drawing findDrawingById(long drawingId);

    public Drawing findDrawingByDesignation(String designation);

}
