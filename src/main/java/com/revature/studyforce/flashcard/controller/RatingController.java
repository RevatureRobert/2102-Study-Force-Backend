package com.revature.studyforce.flashcard.controller;

import com.revature.studyforce.flashcard.dto.RatingDTO;
import com.revature.studyforce.flashcard.dto.RatingResponseDTO;
import com.revature.studyforce.flashcard.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for rating resource handling using {@link RatingService}
 * @author Edson Rodriguez
 */
@RestController
@CrossOrigin
@RequestMapping("/flashcards/rate")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService){
        this.ratingService =ratingService;
    }

    /**
     * Create a new rating record
     * @param rating the data transfer object that contains the flashcardId, userId and difficulty rating to save the new rating record
     * @return A data transfer object that represents the newly created rating object
     */
    @PostMapping("/")
    public RatingResponseDTO createRating(@RequestBody RatingDTO rating ){
        return ratingService.createRating(rating);
    }

    @GetMapping
    public RatingDTO getRating(@PathVariable int flashcardId, @PathVariable int userID){
        return ratingService.getRating(flashcardId,userID);
    }
}
