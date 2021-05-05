package com.revature.studyforce.notification.service;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;

    @MockBean
    private NotificationRepository notificationRepository;

    List<Notification> notificationList = new ArrayList<>();
    Page<Notification> notificationPage;
    Notification notification;
    Timestamp timeToLive;
    FeatureArea featureArea;

    @BeforeEach
    public void setUp(){
        timeToLive = Timestamp.valueOf(LocalDateTime.now().plusDays(3));
        featureArea = FeatureArea.FLASHCARD;
        notification = new Notification(0, "Flashcard Solution", false, timeToLive, featureArea, 1);
        notificationList.add(notification);
        notificationPage = new PageImpl<>(notificationList);
    }

    @Test
    void getAllNotificationsTest(){
        Mockito.doReturn(notificationPage).when(notificationRepository).findAll(any(PageRequest.class));
        Page<Notification>
    }
}
