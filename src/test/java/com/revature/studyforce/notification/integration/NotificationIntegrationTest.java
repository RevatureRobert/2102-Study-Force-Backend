package com.revature.studyforce.notification.integration;

import com.revature.studyforce.notification.controller.NotificationController;
import com.revature.studyforce.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
public class NotificationIntegrationTest {
    private MockMvc mockMvc;

    @Autowired
    private NotificationController notificationController;

    @Autowired
    private NotificationRepository notificationRepository;


}
