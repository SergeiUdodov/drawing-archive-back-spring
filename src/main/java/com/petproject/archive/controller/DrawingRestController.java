package com.petproject.archive.controller;

import com.petproject.archive.entity.Drawing;
import com.petproject.archive.service.DrawingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DrawingRestController {

    @Autowired
    private DrawingService drawingService;

    @GetMapping("/drawings")
    public List<Drawing> findAllDrawings(HttpServletRequest request) {

        return drawingService.findAllDrawings(request);
    }
}
