package com.revature.studyforce.flashcard.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.studyforce.flashcard.controller.TopicController;
import com.revature.studyforce.flashcard.dto.TopicDTO;
import com.revature.studyforce.flashcard.model.Topic;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for TopicController {@link TopicController}
 * @author Kevin Wang
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
class TopicIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    TopicController topicController;

    @Test
    void whenGetAll_shouldReturnListOfTopics() throws Exception {
        List<Topic> topicList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(topicController).build();

        for (int i = 0; i < 5; i++) {
            TopicDTO topicDTO = new TopicDTO(Integer.toString(i));
            topicList.add(new Topic(i + 1, topicDTO.getTopic()));

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/topics")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(topicDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.topicName").value(topicDTO.getTopic()))
                    .andReturn();

            System.out.println(result.getResponse().getContentAsString());
        }

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/topics"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(topicList));
    }

    @Test
    void givenTopicId_whenFindTopicById_shouldReturnTopic() throws Exception {
        List<Topic> topicList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(topicController).build();

        for (int i = 0; i < 5; i++) {
            TopicDTO topicDTO = new TopicDTO(Integer.toString(i));
            topicList.add(new Topic(i + 1, topicDTO.getTopic()));

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/topics")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(topicDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.topicName").value(topicDTO.getTopic()))
                    .andReturn();

            System.out.println(result.getResponse().getContentAsString());
        }

        for (Topic topic : topicList) {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/topics/" + topic.getId()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andReturn();

            Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(topic));
        }
    }

    @Test
    void givenTopicId_whenDeleteTopic_shouldReturnDeletedTopic() throws Exception {
        List<Topic> topicList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(topicController).build();

        for (int i = 0; i < 5; i++) {
            TopicDTO topicDTO = new TopicDTO(Integer.toString(i));
            topicList.add(new Topic(i + 1, topicDTO.getTopic()));

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/topics")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(topicDTO)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.topicName").value(topicDTO.getTopic()))
                    .andReturn();

            System.out.println(result.getResponse().getContentAsString());
        }

        for (Topic topic : topicList) {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/topics/" + topic.getId()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                    .andReturn();

            Assertions.assertEquals(result.getResponse().getContentAsString(), objectMapper.writeValueAsString(topic));
        }

        for (Topic topic : topicList) {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/topics/" + topic.getId()))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andReturn();
        }

        for (Topic topic : topicList) {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/topics/" + topic.getId()))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andReturn();
        }
    }
}
