package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Topic;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * Service class for the TopicRepository {@link TopicRepository}
 * @author Kevin wang
 */
@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    FlashcardRepository flashcardRepository;

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
     * @return the Topic matching the given Id
     */
    public Topic getTopicById(int id) {
        return topicRepository.findById(id).orElse(null);
    }

    /**
     *Add a topic to the database
     * @param topicName The name of the topic to be stored
     * @return The topic object that was stored
     */
    public Topic addTopic(String topicName) {
        Topic topic = new Topic();
        topic.setTopicName(topicName);
        return topicRepository.save(topic);
    }

    /**
     * Remove a topic object from database
     * @param id The id of the topic you want to be removed
     * @return the topic object that was removed
     */
    public Topic deleteTopic(int id) {
        Topic topic = topicRepository.findById(id).orElse(null);
        if (topic == null) {
            return null;
        }

        List<Flashcard> flashcardList = flashcardRepository.findAllByTopicId(id);
        flashcardRepository.deleteAll(flashcardList);
        topicRepository.delete(topic);
        return topic;
    }
}
