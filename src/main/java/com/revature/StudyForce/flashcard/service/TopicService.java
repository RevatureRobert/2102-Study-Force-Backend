package com.revature.StudyForce.flashcard.service;

import com.revature.StudyForce.flashcard.model.Topic;
import com.revature.StudyForce.flashcard.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> getAllTopic() {
        return topicRepository.findAll();
    }

    public Topic getTopicById(int id) {
        return getTopicById(id);
    }

    public Topic addTopic(String topicName) {
        Topic topic = new Topic();
        topic.setTopicName(topicName);
        return topicRepository.save(topic);
    }

    public Topic deleteTopic(int id) {
        Topic topic = topicRepository.findById(id).get();
        topicRepository.delete(topic);
        return topic;
    }
}
