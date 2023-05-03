package com.petproject.archive.service;

import com.petproject.archive.entity.Drawing;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface DrawingService {
    public List<Drawing> findAllDrawings(HttpServletRequest request);
}
