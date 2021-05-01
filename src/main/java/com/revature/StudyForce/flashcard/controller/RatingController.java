package com.revature.StudyForce.flashcard.controller;

import com.revature.StudyForce.flashcard.dto.RatingDTO;
import com.revature.StudyForce.flashcard.dto.RatingResponseDTO;
import com.revature.StudyForce.flashcard.service.RatingService;
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
    @PostMapping("/")
    public RatingResponseDTO createRating(@RequestBody RatingDTO rating ){
        System.out.println("*******************************");
        System.out.println(rating);
        return RATING_SERVICE.createRating(rating);
    }
}
