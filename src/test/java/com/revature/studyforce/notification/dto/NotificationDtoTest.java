package com.revature.studyforce.notification.dto;

import com.revature.studyforce.notification.model.FeatureArea;
import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.dto.NotificationDto;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/***
 * author: Patrick Gonzalez
 */

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class NotificationDtoTest {

    @Test
    void whenConvertToDto_shouldReturnNotificationDto(){
        Integer id = 0;
        String message = "Flashcard";
        boolean isRead = false;
        Timestamp timeToLive = Timestamp.valueOf(LocalDateTime.now().plusDays(3));
        Timestamp createdTime = Timestamp.valueOf(LocalDateTime.now());
        FeatureArea featureArea = FeatureArea.FLASHCARD;
        User user = new User("patrick@revature.net", "Patrick");
        user.setActive(true);
        user.setUserId(1);
        Notification notification = new Notification(id, message, isRead, timeToLive, createdTime, featureArea, user);
        NotificationDto dto = NotificationDto.convertToDto().apply(notification);

        Assertions.assertEquals(id, dto.getId());
        Assertions.assertEquals(message, dto.getMessage());
        Assertions.assertEquals(isRead, dto.isRead());
        Assertions.assertEquals(timeToLive, dto.getTimeToLive());
        Assertions.assertEquals(createdTime, dto.getCreatedTime());
        Assertions.assertEquals(featureArea, dto.getFeatureArea());
        Assertions.assertEquals(user.getUserId(), dto.getUserId());
    }

}
