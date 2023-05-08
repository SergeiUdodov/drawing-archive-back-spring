package com.petproject.archive.service;

import com.petproject.archive.entity.Drawing;
import com.petproject.archive.model.CrmDrawing;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface DrawingService {
    public List<Drawing> findAllDrawings(HttpServletRequest request);
    public Drawing addDrawing(CrmDrawing theDrawing);

    public Drawing updateDrawing(long drawingId, CrmDrawing crmDrawing);

    public void deleteDrawing(long drawingId);

    public Drawing findDrawingById(long drawingId);

}
