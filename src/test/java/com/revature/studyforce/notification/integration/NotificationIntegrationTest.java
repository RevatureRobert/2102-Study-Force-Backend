package com.revature.studyforce.notification.integration;

import com.fasterxml.jackson.core.JsonParser;
import com.revature.studyforce.notification.controller.NotificationController;
import com.revature.studyforce.notification.model.FeatureArea;
import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/***
 * Integration Testing for {@link NotificationController}
 *
 * author: Patrick Gonzalez
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
class NotificationIntegrationTest {
    private MockMvc mockMvc;

    @Autowired
    private NotificationController notificationController;

    @Autowired
    private NotificationRepository notificationRepository;

    LocalDateTime now;
    FeatureArea featureArea;
    Notification notification;
    @BeforeEach
    public void setup(){
        now = LocalDateTime.now();
        featureArea = FeatureArea.FLASHCARD;
        notification = new Notification(0, "Message", false, Timestamp.valueOf(now.plusDays(3)), Timestamp.valueOf(now), featureArea, 1);

        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    void whenGetAllNotifications_shouldReturnStatusOkAndNotificationDtoPage() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"notificationId\": \"0\", \"notificationMessage\": \"Message\", \"read\": \"false\", " +
                        "\"timeToLive\": \""+ Timestamp.valueOf(now.plusDays(3))+ "\", \"createdTime\": \"" + Timestamp.valueOf(now) + "\"" +
                "\"featureArea\": \"FLASHCARD\", \"applicationUserId\": \"1\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.notificationId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.notificationMessage").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.read").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeToLive").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTime").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.featureArea").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.applicationUserId").isNumber())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getStatus());
    }

    @Test
    void whenGetNotificationsByUserId_shouldReturnStatusOkAndNotificationDto(){

    }

    @Test
    void addNotificationTest(){

    }

    @Test
    void updateNotificationTest(){

    }

    @Test
    void deleteAllNotificationsByUserId(){

    }

}
