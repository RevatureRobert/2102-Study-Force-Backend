package com.revature.studyforce.flashcard.controller;

import com.revature.studyforce.flashcard.dto.NewQuizDTO;
import com.revature.studyforce.flashcard.dto.QuizDTO;
import com.revature.studyforce.flashcard.dto.UpdateQuizDTO;
import com.revature.studyforce.flashcard.model.Quiz;
import com.revature.studyforce.flashcard.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/flashcards/quiz")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }

    @GetMapping
    public @ResponseBody Page<QuizDTO> getAll(
            @RequestParam(name="page", defaultValue = "0", required = false) int page,
            @RequestParam(name="offset", defaultValue = "10", required = false) int offset,
            @RequestParam(name="sortby", defaultValue = "quizId", required = false) String sortBy,
            @RequestParam(name = "order", defaultValue = "ASC", required = false) String order)
    {
        return quizService.getAll(page,offset,sortBy,order);
    }

    @PostMapping
    public @ResponseBody Quiz createQuiz(@RequestBody NewQuizDTO quizDTO){
        return quizService.createQuiz(quizDTO);
    }

    @PutMapping
    public @ResponseBody QuizDTO updateQuiz(@RequestBody UpdateQuizDTO quizDTO){
        return quizService.updateQuiz(quizDTO);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody void deleteQuiz(@PathVariable Integer id){
        quizService.deleteQuiz(id);
    }

}
