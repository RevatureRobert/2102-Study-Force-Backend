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
     * Retrieves page of QuizDTO entries with pagination {@link QuizService#getAll(int, int, String, String)}
     * @param page which page to display
     * @param offset how many entries per page
     * @param sortBy attribute to sort by [ quizId | quizName | quizUser ]
     * @param order asc or desc
     * @return page of QuizDTO entries
     */
    @GetMapping
    public Page<QuizDTO> getAll(
            @RequestParam(name="page", defaultValue = "0", required = false) int page,
            @RequestParam(name="offset", defaultValue = "10", required = false) int offset,
            @RequestParam(name="sortby", defaultValue = "quizId", required = false) String sortBy,
            @RequestParam(name = "order", defaultValue = "ASC", required = false) String order)
    {
        return quizService.getAll(page,offset,sortBy,order);
    }

    /**
     * GET request for 'getQuizById' in {@link QuizService#getById(int)}
     * @param quizId Id of quiz to be returned
     * @return single QuizDto that matches Id param
     */
    public QuizDTO getByQuizId(Integer quizId){
        return quizService.getById(quizId);
    }

    /**
     * Creates a Quiz through a supplied DTO {@link QuizService#createQuiz(NewQuizDTO)}
     * @param quizDTO dto for incoming quiz
     * @return a new Quiz
     */
    @PostMapping
    public QuizDTO createQuiz(@RequestBody NewQuizDTO quizDTO){
        return quizService.createQuiz(quizDTO);
    }

    /**
     * Updates given QuizDTO such that the quizId is specified {@link QuizService#updateQuiz(UpdateQuizDTO)}
     * @param quizDTO QuizDTO with quizId of he Quiz to be modified
     * @return updated QuizDTO
     */
    @PutMapping
    public QuizDTO updateQuiz(@RequestBody UpdateQuizDTO quizDTO){
        return quizService.updateQuiz(quizDTO);
    }

    /**
     * Deletes given QuizDTO such that the quizId is specified {@link QuizService#deleteQuiz(int)}
     * @param id id of the quiz to be deleted
     */
    @DeleteMapping("/{id}")
    public @ResponseBody void deleteQuiz(@PathVariable Integer id){
        quizService.deleteQuiz(id);
    }

}
