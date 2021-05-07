package com.revature.studyforce.flashcard.controller;

import com.revature.studyforce.flashcard.model.Rating;
import com.revature.studyforce.flashcard.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for rating resource handling using {@link RatingService}
 * @author Edson Rodriguez
 */
@RestController
@RequestMapping("/flashcards/rate")
public class RatingController {

    private final RatingService RATING_SERVICE;

    @Autowired
    public RatingController(RatingService ratingService){
        this.RATING_SERVICE=ratingService;
    }

    /**
     * Create a new rating record
     * @param rating the data transfer object that contains the flashcardId, userId and difficulty rating to save the new rating record
     * @return A data transfer object that represents the newly created rating object
     */
    //TODO: change the return type for a DTO
    @PostMapping("/")
    public Rating createRating(@RequestBody Rating rating ){
        return RATING_SERVICE.createRating(rating);
    }
}
