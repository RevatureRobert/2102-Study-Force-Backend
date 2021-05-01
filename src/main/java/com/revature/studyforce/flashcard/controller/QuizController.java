package com.revature.studyforce.flashcard.controller;

import com.revature.studyforce.flashcard.dto.QuizDTO;
import com.revature.studyforce.flashcard.model.Quiz;
import com.revature.studyforce.flashcard.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flashcards/quiz")
public class QuizController {

    private final QuizService QUIZ_SERVICE;

    @Autowired
    public QuizController(QuizService quizService){
        this.QUIZ_SERVICE = quizService;
    }

    @GetMapping("/all")
    public Page<QuizDTO> getAllQuizzes(Pageable page){
        return QUIZ_SERVICE.getAll(page);
    }

    @PostMapping
    public QuizDTO createQuiz(@RequestBody QuizDTO quizDTO){
        quizDTO.setQuizId(0);
        Quiz q = QuizDTO.DTOToQuiz().apply(quizDTO);
        return QUIZ_SERVICE.createQuiz(q);
    }

    @PutMapping
    public QuizDTO updateQuiz(@RequestBody QuizDTO quizDTO){
        return QUIZ_SERVICE.updateQuiz(quizDTO);
    }

    @DeleteMapping
    public void deleteQuiz(@RequestBody QuizDTO quizDTO){
        QUIZ_SERVICE.deleteQuiz(quizDTO);
    }

}
