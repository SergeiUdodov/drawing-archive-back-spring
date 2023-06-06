package com.petproject.archive.controller;

import com.petproject.archive.entity.Drawing;
import com.petproject.archive.model.CrmDrawing;
import com.petproject.archive.service.DrawingService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.petproject.archive.model.CrmRequest;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DrawingRestController {

    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private DrawingService drawingService;

    @GetMapping("/drawings/")
    public List<Drawing> findAllDrawings() {

        return drawingService.findAllDrawings();

    }

    @GetMapping("/drawings/{pathVariable}")
    public List<Drawing> findAllDrawingsByRequest(@PathVariable String pathVariable) {

        return drawingService.findAllDrawingsByRequest(pathVariable);

    }

    @PostMapping("/addDrawing")
    public void addDrawing(@RequestBody CrmDrawing crmDrawing, HttpServletRequest request){

        drawingService.addDrawing(crmDrawing, request);
    }

    @GetMapping("/drawingByDesignation/{designation}")
    public Drawing findDrawingByDesignation(@PathVariable String designation){

        return drawingService.findDrawingByDesignation(designation);
    }

    @PutMapping("/updateDrawing/{drawingId}")
    public void updateDrawing(@PathVariable long drawingId, @RequestBody CrmDrawing crmDrawing, HttpServletRequest request){

        drawingService.updateDrawing(drawingId, crmDrawing, request);
    }

    @DeleteMapping("/deleteDrawing/{drawingId}")
    public String deleteDrawing(@PathVariable long drawingId){

        drawingService.deleteDrawing(drawingId);
        return "Deleted drawing id - " + drawingId;
    }
}
