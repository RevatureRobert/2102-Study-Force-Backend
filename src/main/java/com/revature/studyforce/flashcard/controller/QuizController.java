package com.revature.studyforce.flashcard.controller;

import com.revature.studyforce.flashcard.dto.NewQuizDTO;
import com.revature.studyforce.flashcard.dto.QuizDTO;
import com.revature.studyforce.flashcard.dto.UpdateQuizDTO;
import com.revature.studyforce.flashcard.model.Quiz;
import com.revature.studyforce.flashcard.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for HTTP requests pertaining to Quiz class {@link QuizService}
 * @author Nick Zimmerman
 */
@RestController
@CrossOrigin
@RequestMapping("/flashcards/quiz")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }

    /**
     * Retrieves page of QuizDTO entries with pagination
     * @param page which page to display
     * @param offset how many entries per page
     * @param sortBy attribute to sort by
     * @param order asc or desc
     * @return page of QuizDTO entries
     */
    @GetMapping
    public @ResponseBody Page<QuizDTO> getAll(
            @RequestParam(name="page", defaultValue = "0", required = false) int page,
            @RequestParam(name="offset", defaultValue = "10", required = false) int offset,
            @RequestParam(name="sortby", defaultValue = "quizId", required = false) String sortBy,
            @RequestParam(name = "order", defaultValue = "ASC", required = false) String order)
    {
        return quizService.getAll(page,offset,sortBy,order);
    }

    /**
     * Creates a Quiz through a supplied DTO
     * @param quizDTO dto for incoming quiz
     * @return a new Quiz
     */
    @PostMapping
    public @ResponseBody Quiz createQuiz(@RequestBody NewQuizDTO quizDTO){
        return quizService.createQuiz(quizDTO);
    }

    /**
     * Updates given QuizDTO such that the quizId is specified
     * @param quizDTO QuizDTO with quizId of he Quiz to be modified
     * @return updated QuizDTO
     */
    @PutMapping
    public @ResponseBody QuizDTO updateQuiz(@RequestBody UpdateQuizDTO quizDTO){
        return quizService.updateQuiz(quizDTO);
    }

    /**
     * Deletes given QuizDTO such that the quizId is specified
     * @param id id of the quiz to be deleted
     */
    @DeleteMapping("/{id}")
    public @ResponseBody void deleteQuiz(@PathVariable Integer id){
        quizService.deleteQuiz(id);
    }

}
