package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.model.Topic;
import com.revature.studyforce.flashcard.repository.TopicRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class TopicServiceTest {

    @MockBean
    private TopicRepository topicRepository;

    @Autowired
    private TopicService topicService;

    @Test
    void whenGetAll() {

        List<Topic> topicList = new ArrayList<>();
        topicList.add(new Topic(1, "1"));
        topicList.add(new Topic(2, "2"));
        topicList.add(new Topic(3, "3"));

        Mockito.when(topicRepository.findAll()).thenReturn(topicList);

        List<Topic> response = topicService.getAllTopic();

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.get(0), topicList.get(0));
        Assertions.assertEquals(response.get(1), topicList.get(1));
        Assertions.assertEquals(response.get(2), topicList.get(2));
    }

    @Test
    void getTopicByIdTest() {
        Topic topic = new Topic(1, "1");

        Mockito.when(topicRepository.findById(0)).thenReturn(Optional.empty());
        Mockito.when(topicRepository.findById(1)).thenReturn(Optional.of(topic));

        Topic response = topicService.getTopicById(0);
        Assertions.assertNull(response);

        response = topicService.getTopicById(1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, topic);
    }

    @Test
    void addTopicTest() {
        Topic topic = new Topic(0, "1");

        Mockito.when(topicRepository.save(topic)).thenReturn(new Topic(1, "1"));

        Topic response = topicService.addTopic("1");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getId(), 1);
        Assertions.assertEquals(response.getTopicName(), "1");
    }

    @Test
    void deleteTopic() {
        Topic topic = new Topic(1, "1");

        Mockito.when(topicRepository.findById(0)).thenReturn(Optional.empty());
        Mockito.when(topicRepository.findById(1)).thenReturn(Optional.of(topic));

        Topic response = topicService.deleteTopic(0);
        Assertions.assertNull(response);

        response = topicService.deleteTopic(1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, topic);
    }
}
