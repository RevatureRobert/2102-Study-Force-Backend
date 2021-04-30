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

    /**
     * Retrieve all topics from database
     * @return list of all topic
     */
    public List<Topic> getAllTopic() {
        return topicRepository.findAll();
    }

    /**
     * Retrieve topic of the specified id
     * @param id The id given to the topic element on the database
     * @return
     */
    public Topic getTopicById(int id) {
        return topicRepository.findById(id).orElse(null);
    }

    /**
     *
     * @param topicName
     * @return
     */
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