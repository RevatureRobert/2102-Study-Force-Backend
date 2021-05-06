package com.revature.studyforce.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.studyforce.notification.dto.NotificationDto;
import com.revature.studyforce.notification.model.Notification;
import com.revature.studyforce.notification.repository.NotificationRepository;
import com.revature.studyforce.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.List;

/***
 * Author: Patrick
 */

@SpringBootTest
public class NotificationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private NotificationRepository notificationRepository;

    List<Notification> notificationList = new ArrayList<>();
    Page<Notification> notificationPage;
    NotificationDto notificationDto;
    Notification notification;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        notificationDto = new NotificationDto();
        notification = new Notification();
        notification.setNotificationId(1);
        notificationList.add(notification);
        notificationPage = new PageImpl<>(notificationList);
    }

    @Test
    public void getAllNotificationsTest() throws Exception{
        Mockito.doReturn(notificationPage).when(notificationRepository).findAll(any(PageRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.get("/notifications")
            .content(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
        verify(notificationRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void getNotificationsByUserId() throws Exception{
        Mockito.doReturn(notificationPage).when(notificationRepository).findByApplicationUserId(1, any(PageRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.get("/notifications/id")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
        verify(notificationRepository, times(1)).findByApplicationUserId(1, any(PageRequest.class));
    }

    @Test
    public void addNotificationTest() throws Exception{
        Mockito.doReturn(notificationPage).when(notificationRepository).findAll(any(PageRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/notifications")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    public void updateNotificationTest() throws Exception{
        Mockito.doReturn(notificationPage).when(notificationRepository).findAll(any(PageRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/notifications")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(notificationRepository, times(1)).findById(1);
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    public void deleteNotificationsByUserIdTest() throws Exception{
        Mockito.doReturn(notificationPage).when(notificationRepository).findAll(any(PageRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/notifications")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
        verify(notificationRepository, times(1)).deleteByApplicationUserId(1);
    }

}
