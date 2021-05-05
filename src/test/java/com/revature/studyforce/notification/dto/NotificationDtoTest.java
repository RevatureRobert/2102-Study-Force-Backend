package com.revature.studyforce.notification.dto;

import com.revature.studyforce.notification.model.FeatureArea;
import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.dto.NotificationDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
public class NotificationDtoTest {

    @Test
    void ConvertToDtoTest(){
        Integer id = 0;
        String message = "Flashcard";
        boolean isRead = false;
        Timestamp timeToLive = Timestamp.valueOf(LocalDateTime.now().plusDays(3));
        Timestamp createdTime = Timestamp.valueOf(LocalDateTime.now());
        FeatureArea featureArea = FeatureArea.FLASHCARD;
        Integer userId = 1;
        Notification notification = new Notification(id, message, isRead, timeToLive, createdTime, featureArea, userId);
        NotificationDto dto = new NotificationDto(notification);

        Assertions.assertEquals(id, dto.getId());
        Assertions.assertEquals(message, dto.getMessage());
        Assertions.assertEquals(isRead, dto.isRead());
        Assertions.assertEquals(timeToLive, dto.getTimeToLive());
        Assertions.assertEquals(createdTime, dto.getCreatedTime());
        Assertions.assertEquals(featureArea, dto.getFeatureArea());
        Assertions.assertEquals(userId, dto.getUserId());
    }

    @Test
    void ConvertFromDtoTest(){
        Integer id = 0;
        String message = "Flashcard";
        boolean isRead = false;
        Timestamp timeToLive = Timestamp.valueOf(LocalDateTime.now().plusDays(3));
        Timestamp createdTime = Timestamp.valueOf(LocalDateTime.now());
        FeatureArea featureArea = FeatureArea.FLASHCARD;
        Integer userId = 1;
        NotificationDto notificationDto = new NotificationDto(id, message, isRead, timeToLive, createdTime, featureArea, userId);
        Notification notification = new Notification(notificationDto);

        Assertions.assertEquals(id, notification.getNotificationId());
        Assertions.assertEquals(message, notification.getNotificationMessage());
        Assertions.assertEquals(isRead, notification.isRead());
        Assertions.assertEquals(timeToLive, notification.getTimeToLive());
        Assertions.assertEquals(createdTime, notification.getCreatedTime());
        Assertions.assertEquals(featureArea, notification.getFeatureArea());
        Assertions.assertEquals(userId, notification.getApplicationUserId());
    }
}
