package com.petproject.archive.dao;

import com.petproject.archive.entity.Drawing;

import java.util.List;

public interface DrawingDao {
    public List<Drawing> findAllDrawings();

    public void addDrawing(Drawing newDrawing);

    public void updateDrawing(Drawing currentDrawing);

    public void deleteDrawing(long drawingId);

    public Drawing findDrawingById(long drawingId);

    public Drawing findDrawingByDesignation(String designation);

}
