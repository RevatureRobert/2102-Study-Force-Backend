package com.revature.StudyForce.flashcard.controller;

import com.revature.StudyForce.flashcard.model.Topic;
import com.revature.StudyForce.flashcard.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping
    public List<Topic> getAllTopic() {
        return topicService.getAllTopic();
    }
}
