package com.revature.StudyForce.flashcard.controller;

import com.revature.StudyForce.flashcard.dto.TopicDTO;
import com.revature.StudyForce.flashcard.model.Topic;
import com.revature.StudyForce.flashcard.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    @ResponseBody
    public List<Topic> getAllTopic() {
        return topicService.getAllTopic();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Topic findTopicById(@PathVariable int id) {
        return topicService.getTopicById(id);
    }

    //probaly admin only
    @PostMapping
    @ResponseBody
    public Topic createTopic(@RequestBody TopicDTO topicDTO) {
        return topicService.addTopic(topicDTO.getTopic());
    }

    //Probaly admin only
    @DeleteMapping("/{id}")
    @ResponseBody
    public Topic deleteTopic(@PathVariable int id) {
        return topicService.deleteTopic(id);
    }

}
