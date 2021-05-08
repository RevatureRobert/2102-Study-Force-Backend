package com.revature.studyforce.notification.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.studyforce.notification.controller.NotificationController;
import com.revature.studyforce.notification.dto.NotificationDto;
import com.revature.studyforce.notification.model.FeatureArea;
import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.repository.NotificationRepository;
import com.revature.studyforce.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Patrick Gonzalez
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
class NotificationIntegrationTest{

    private MockMvc mockMvc;

    @Autowired
    private NotificationController notificationController;

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;

    LocalDateTime now = LocalDateTime.now();
    FeatureArea featureArea = FeatureArea.FLASHCARD;
    Notification notification;
    NotificationDto notificationDto;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup(){
        notification = new Notification(0, "Message", false,
                Timestamp.valueOf(now.plusDays(3)), Timestamp.valueOf(now), featureArea, 1);

        notificationDto = new NotificationDto(0, "Hello", true,
                null, null, FeatureArea.STACKTRACE, 1);
        notificationDto.setCreatedTime(null);
        notificationDto.setTimeToLive(null);

        notification = notificationRepository.save(notification);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    void getAllNotificationsTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/notifications"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].read").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].timeToLive").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].createdTime").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].featureArea").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].userId").isNumber());
    }

    @Test
    void getAllNotificationsByUserIdTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/notifications/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].read").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].timeToLive").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].createdTime").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].featureArea").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].userId").isNumber());
    }

    @Test
    void addNotificationTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(notificationDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.read").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeToLive").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTime").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.featureArea").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").isNumber());
    }

    @Test
    void updateNotificationTest() throws Exception{
        notificationDto = NotificationDto.convertToDto().apply(notification);
        notificationDto.setMessage("Whatever");
        notificationDto.setTimeToLive(null);
        notificationDto.setCreatedTime(null);
        mockMvc.perform(MockMvcRequestBuilders.put("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(notificationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.read").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeToLive").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTime").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.featureArea").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").isNumber());
    }

//    @Test
//    void deleteByNotificationIdTest() throws Exception{
//        notificationDto = NotificationDto.convertToDto().apply(notification);
//        mockMvc.perform(MockMvcRequestBuilders.delete("/notifications/" + notificationDto.getId()));
////                .andExpect(MockMvcResultMatchers.status().isNoContent())
////                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
////                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.read").isBoolean())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.timeToLive").isNumber())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTime").isNumber())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.featureArea").isString())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").isNumber());
//
//        verify(notificationService, times(1)).deleteByNotificationId(notificationDto.getId());
//    }

//    @Test
//    void deleteNotificationByUserIdTest() throws Exception{
//
//    }
}