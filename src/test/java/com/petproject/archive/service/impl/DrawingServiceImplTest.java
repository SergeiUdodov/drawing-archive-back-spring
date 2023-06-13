package com.petproject.archive.service.impl;

import com.petproject.archive.dao.DrawingDao;
import com.petproject.archive.entity.Drawing;
import com.petproject.archive.entity.User;
import com.petproject.archive.model.CrmDrawing;
import com.petproject.archive.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DrawingServiceImplTest {

    @Mock
    private DrawingDao drawingDao;

    @Mock
    private HttpServletRequest request1;

    @Mock
    private HttpServletRequest request2;

    @Mock
    private UserService userService;

    @InjectMocks
    private DrawingServiceImpl drawingServiceImpl;

    @Test
    void testFindAllDrawings() {

        List<Drawing> mockDrawings = getMockDrawings();

        Mockito.when(drawingDao.findAllDrawings()).thenReturn(getMockDrawings());

        List<Drawing> result = drawingServiceImpl.findAllDrawings();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockDrawings.get(2), result.get(0));
        Assertions.assertEquals(mockDrawings.get(1), result.get(1));
        Assertions.assertEquals(mockDrawings.get(0), result.get(2));

    }

    @Test
    void testFindAllDrawingsByRequest() {

        List<Drawing> mockDrawings = getMockDrawings();

        Mockito.when(drawingDao.findAllDrawings()).thenReturn(getMockDrawings());

        List<Drawing> result = drawingServiceImpl.findAllDrawingsByRequest("1111111");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(mockDrawings.get(1), result.get(0));
        Assertions.assertEquals(1, result.size());

    }

    @Test
    void testAddDrawing(){

        CrmDrawing crmDrawing = getMockCrmDrawing();
        User user = getMockUser();

        Mockito.when(userService.getUserByToken(request1)).thenReturn(user);
        Mockito.when(userService.getUserByToken(request2)).thenReturn(null);

        Drawing result = drawingServiceImpl.addDrawing(crmDrawing, request1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(crmDrawing.getDesignation(), result.getDesignation());
        Assertions.assertEquals(crmDrawing.getName(), result.getName());
        Assertions.assertEquals(crmDrawing.getImageURL(), result.getImageURL());
        Assertions.assertThrows(RuntimeException.class, () ->{drawingServiceImpl.addDrawing(crmDrawing, request2);});

    }

    @Test
    void testUpdateDrawing(){

        CrmDrawing crmDrawing = getMockCrmDrawing();
        User user = getMockUser();

        Mockito.when(drawingServiceImpl.findDrawingById(0)).thenReturn(new Drawing());
        Mockito.when(drawingServiceImpl.findDrawingById(1)).thenReturn(null);
        Mockito.when(userService.getUserByToken(request1)).thenReturn(user);
        Mockito.when(userService.getUserByToken(request2)).thenReturn(null);

        Drawing result = drawingServiceImpl.updateDrawing(0, crmDrawing, request1);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(crmDrawing.getDesignation(), result.getDesignation());
        Assertions.assertEquals(crmDrawing.getName(), result.getName());
        Assertions.assertEquals(crmDrawing.getImageURL(), result.getImageURL());
        Assertions.assertThrows(RuntimeException.class, () ->{drawingServiceImpl.updateDrawing(1, crmDrawing, request1);});
        Assertions.assertThrows(RuntimeException.class, () ->{drawingServiceImpl.updateDrawing(0, crmDrawing, request2);});
    }

    @Test
    void testDeleteDrawing(){

        Mockito.when(drawingServiceImpl.findDrawingById(0)).thenReturn(null);

        Assertions.assertThrows(RuntimeException.class, () ->{drawingServiceImpl.deleteDrawing(0);});
    }

    private List<Drawing> getMockDrawings(){
        List<Drawing> mockDrawings = new ArrayList<>();

        Drawing drawing2 = new Drawing("2222222", "drawing2", 1, "12.06.2023 18:45:44", "url2");
        Drawing drawing1 = new Drawing("1111111", "drawing1", 1, "12.06.2023 18:41:44", "url1");
        Drawing drawing0 = new Drawing("0000000", "drawing0", 1, "12.06.2023 18:42:44", "url0");

        mockDrawings.add(drawing2);
        mockDrawings.add(drawing1);
        mockDrawings.add(drawing0);

        return mockDrawings;
    }

    private CrmDrawing getMockCrmDrawing(){
        CrmDrawing crmDrawing = new CrmDrawing();

        crmDrawing.setDesignation("1111111");
        crmDrawing.setName("mockName");
        crmDrawing.setImageURL("url1");

        return crmDrawing;
    }

    private User getMockUser(){
        return new User("test@mail.ru", "firstName", "lastName", "123");
    }
}
