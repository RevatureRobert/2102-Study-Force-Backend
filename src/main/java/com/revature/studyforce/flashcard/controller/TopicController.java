package com.revature.studyforce.flashcard.controller;

import com.revature.studyforce.flashcard.dto.TopicDTO;
import com.revature.studyforce.flashcard.model.Topic;
import com.revature.studyforce.flashcard.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/topics")
public class TopicController {


    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService){
        this.topicService = topicService;
    }

    /**
     * Get a list of all topic in json format
     * @return list of topic array
     */
    @GetMapping
    @ResponseBody
    public List<Topic> getAllTopic() {
        return topicService.getAllTopic();
    }

    /**
     * Get a specific id
     * @param id The id of the topic to get
     * @return Topic of the specified id
     * @exception throw 404 if topic not found
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Topic findTopicById(@PathVariable int id) {
        Topic topic = topicService.getTopicById(id);
        if (topic == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic doesn't exist");
        }
        return topic;
    }

    /**
     * Add a topic to database (need to add auth for admin only)
     * @param topicDTO DTO with just the name of the topic
     * @return The topic object to be stored
     */
    @PostMapping
    @ResponseBody
    public Topic createTopic(@RequestBody TopicDTO topicDTO) {
        return topicService.addTopic(topicDTO.getTopic());
    }

    /**
     * Delete an topic from database (need to add auth for admin only)
     * @param id The id you want to remove
     * @return The topic that was removed
     * @exception throws 404 if topic not found
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Topic deleteTopic(@PathVariable int id) {
        Topic topic = topicService.deleteTopic(id);
        if (topic == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Topic doesn't exist");
        }
        return topic;
    }

}
