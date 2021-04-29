package com.revature.StudyForce.services;

import com.revature.StudyForce.models.Topic;
import com.revature.StudyForce.repositories.TopicRepository;
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

    public
}
