package com.revature.studyforce.notification.service;

import com.revature.studyforce.notification.dto.NotificationDto;
import com.revature.studyforce.notification.model.FeatureArea;
import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.repository.NotificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;

    @MockBean
    private NotificationRepository notificationRepository;

    List<Notification> notificationList = new ArrayList<>();
    Page<Notification> notificationPage;
    Notification notification;
    LocalDateTime now;
    Timestamp timeToLive;
    FeatureArea featureArea;
    NotificationDto notificationDto;

    @BeforeEach
    public void setUp(){
        now = LocalDateTime.now();
        timeToLive = Timestamp.valueOf(now.plusDays(3));
        featureArea = FeatureArea.FLASHCARD;
        notification = new Notification(0, "Flashcard Solution", false, timeToLive, Timestamp.valueOf(now), featureArea, 1);
        notificationDto = new NotificationDto(notification);
        notificationList.add(notification);
        notificationPage = new PageImpl<>(notificationList);
    }

    @Test
    void findAllTest(){
        Mockito.doReturn(notificationPage).when(notificationRepository).findAll(any(PageRequest.class));
        Page<NotificationDto> dtos = notificationService.findAll();
        NotificationDto dto = dtos.getContent().get(0);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(0, dto.getId());
        Assertions.assertFalse(dto.isRead());
        Assertions.assertEquals("Flashcard Solution", dto.getMessage());
        Assertions.assertEquals(Timestamp.valueOf(now.plusDays(3)), dto.getTimeToLive());
        Assertions.assertEquals(Timestamp.valueOf(now), dto.getCreatedTime());
        Assertions.assertEquals(1, dto.getUserId());
        Assertions.assertEquals(featureArea, dto.getFeatureArea());
    }

    @Test
    void findByUserIdTest(){
        Mockito.doReturn(notificationPage).when(notificationRepository).findByApplicationUserId(1, any(PageRequest.class));
        Page<NotificationDto> dtos = notificationService.findByUserId(1, 0);
        NotificationDto dto = dtos.getContent().get(0);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(0, dto.getId());
        Assertions.assertFalse(dto.isRead());
        Assertions.assertEquals("Flashcard Solution", dto.getMessage());
        Assertions.assertEquals(Timestamp.valueOf(now.plusDays(3)), dto.getTimeToLive());
        Assertions.assertEquals(Timestamp.valueOf(now), dto.getCreatedTime());
        Assertions.assertEquals(1, dto.getUserId());
        Assertions.assertEquals(featureArea, dto.getFeatureArea());
    }

    void saveTest(){
        Mockito.doReturn(notification).when(notificationRepository).save(any(Notification.class));
        NotificationDto dto = notificationService.save(notificationDto);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(0, dto.getId());
        Assertions.assertFalse(dto.isRead());
        Assertions.assertEquals("Flashcard Solution", dto.getMessage());
        Assertions.assertEquals(Timestamp.valueOf(now.plusDays(3)), dto.getTimeToLive());
        Assertions.assertEquals(Timestamp.valueOf(now), dto.getCreatedTime());
        Assertions.assertEquals(1, dto.getUserId());
        Assertions.assertEquals(featureArea, dto.getFeatureArea());
    }

    @Test
    void updateTest(){
        Mockito.doReturn(notification).when(notificationRepository).findById(1);
        Mockito.doReturn(null).when(notificationRepository).save(notification);
        NotificationDto dto = notificationService.update(notificationDto);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(0, dto.getId());
        Assertions.assertFalse(dto.isRead());
        Assertions.assertEquals("Flashcard Solution", dto.getMessage());
        Assertions.assertEquals(Timestamp.valueOf(now.plusDays(3)), dto.getTimeToLive());
        Assertions.assertEquals(Timestamp.valueOf(now), dto.getCreatedTime());
        Assertions.assertEquals(1, dto.getUserId());
        Assertions.assertEquals(featureArea, dto.getFeatureArea());
    }

    @Test
    void deleteTest(){
        Mockito.doReturn(notification).when(notificationRepository).findById(1);
        NotificationDto dto = notificationService.delete(notificationDto);
    }
}
