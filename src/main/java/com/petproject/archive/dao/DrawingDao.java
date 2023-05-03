package com.petproject.archive.dao;

import com.petproject.archive.entity.Drawing;

import java.util.List;

public interface DrawingDao {
    public List<Drawing> findAllDrawings();
}
