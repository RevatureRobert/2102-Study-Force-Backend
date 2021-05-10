package com.revature.studyforce.notification.service;

import com.revature.studyforce.notification.dto.NotificationDto;
import com.revature.studyforce.notification.model.FeatureArea;
import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.repository.NotificationRepository;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

/**
 * Tests for {@link NotificationService}
 * @author Patrick Gonzalez
 */

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class NotificationServiceTest {

    @MockBean
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    List<Notification> notificationList = new ArrayList<>();
    Page<Notification> notificationPage;
    Notification notification;
    LocalDateTime now;
    Timestamp timeToLive;
    FeatureArea featureArea;
    NotificationDto notificationDto;
    User user ;

    @BeforeEach
    public void setUp(){
        now = LocalDateTime.now();
        timeToLive = Timestamp.valueOf(now.plusDays(3));
        featureArea = FeatureArea.FLASHCARD;
        user = new User("patrick@revature.net", "Patrick");

        notification = new Notification(0, "Flashcard Solution", false, timeToLive, Timestamp.valueOf(now), featureArea, 1 ,user );


        user.setActive(true);
        user.setUserId(1);
      
        notificationDto = NotificationDto.convertToDto().apply(notification);
        notificationList.add(notification);
        notificationPage = new PageImpl<>(notificationList);
    }

    @Test
    void findAllTest(){
        Mockito.doReturn(notificationPage).when(notificationRepository).findAll(any(PageRequest.class));
        Page<NotificationDto> dtos = notificationService.findAll(0, 5);
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
        Mockito.doReturn(notificationPage).when(notificationRepository).findByUser_UserId(eq(notification.getUser().getUserId()), any(PageRequest.class));
        Page<NotificationDto> dtos = notificationService.findByUserId(1, 0, 10);
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
        Mockito.when(notificationRepository.findById(notification.getNotificationId())).thenReturn(Optional.of(notification));
        Mockito.when(notificationRepository.save(ArgumentMatchers.isA(Notification.class))).thenReturn(notification);
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

//    @Test
//    void deleteTest(){
//        notificationService.delete(notificationDto);
//        verify(notificationRepository, times(1)).delete(notification);
//    }
}
