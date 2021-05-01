package com.revature.StudyForce.flashcard.service.intgration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.StudyForce.flashcard.controller.TopicController;
import com.revature.StudyForce.flashcard.dto.TopicDTO;
import com.revature.StudyForce.flashcard.model.Topic;
import com.revature.StudyForce.flashcard.repository.TopicRepository;
import org.junit.jupiter.api.Assertions;
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

import java.util.LinkedList;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
public class TopicIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    TopicController topicController;

    @Autowired
    TopicRepository topicRepository;

    @Test
    void topicIntegrationTest1() throws Exception {
        List<Topic> topicList = new LinkedList();
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(topicController).build();

        for (int i = 0; i < 5; i++) {
            TopicDTO topicDTO = new TopicDTO(Integer.toString(i));
            topicList.add(new Topic(i + 1, topicDTO.getTopic()));

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/topic")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(topicDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.topicName").value(topicDTO.getTopic()))
                    .andReturn();

            System.out.println(result.getResponse().getContentAsString());
        }

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/topic"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(topicList));


    }
}
