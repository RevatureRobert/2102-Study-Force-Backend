package com.revature.studyforce.flashcard.controller;

import com.revature.studyforce.flashcard.dto.AnswerDTO;
import com.revature.studyforce.flashcard.dto.DeleteAnswerDTO;
import com.revature.studyforce.flashcard.model.Answer;
import com.revature.studyforce.flashcard.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for the Answer resource handling using {@link AnswerService}
 * @author Edson Rodriguez
 */
@RestController
@RequestMapping("/flashcards/answers")
public class AnswerController {

    private AnswerService ANSWER_SERVICE;

    @Autowired
    public AnswerController(AnswerService answerService){
        this.ANSWER_SERVICE=answerService;
    }

    /**
     * Method used to retrieve a collection of answers with pagination
     * @param id The id of the flashcard from which the answers are being fetched
     * @param page The page to be selected defaults to 0 if page could not be understood
     * @param offset The number of elements per page [5|10|25|50|100] defaults to 25 if offset could not be understood
     * @param sortBy The property/field to sort by ["answerScore"|"creationTime"|"resolutionTime"|"creator"] defaults to creator if sortby could not be understood
     * @param order The order in which the list is displayed ["ASC"|"DESC"]
     * @return a Page of answers that match the Flashcard id with pagination and sorting applied
     */
    @GetMapping("/flashcard-id/{flashcardId}")
    public Page<Answer> getAllAnswersByFlashcardId(
            @PathVariable(name = "flashcardId") int id,
            @RequestParam(value="page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "offset", required = false, defaultValue = "25") int offset,
            @RequestParam(value = "sortby", required = false, defaultValue = "answerScore") String sortBy,
            @RequestParam(value = "order", required = false, defaultValue = "ASC") String order){
        return ANSWER_SERVICE.getAllByFlashcardId(id,page,offset,sortBy,order);
    }

    /**
     * Deletes an answer by its answerId
     * @param deleteAnswerDTO the data transfer object that contains the id of the answer to delete
     * @return a confirmation message if the object was deleted
     */
    @DeleteMapping("/")
    public String deleteAnswerById(@RequestBody DeleteAnswerDTO deleteAnswerDTO){
        ANSWER_SERVICE.deleteAnswerById(deleteAnswerDTO.getAnswerId());
        return "Deleted answer with id:" + deleteAnswerDTO.getAnswerId();
    }

    /**
     * Method used to create a new Answer
     * @param answerDTO The dta transfer object with the information required to create a new answer {@link AnswerDTO}
     * @return The newly created answer object
     */
    @PostMapping("/")
    public Answer createNewAnswer(@RequestBody AnswerDTO answerDTO){
        return ANSWER_SERVICE.createAnswer(answerDTO);
    }


}
