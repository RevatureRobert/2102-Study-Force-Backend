package com.revature.studyforce.flashcard.controller;




import com.revature.studyforce.flashcard.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flashcards/answers")
public class AnswerController {

    private AnswerService ANSWER_SERVICE;

    @Autowired
    public AnswerController(AnswerService answerService){
        this.ANSWER_SERVICE=answerService;
    }


}
