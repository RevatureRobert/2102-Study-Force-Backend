package com.revature.studyforce.flashcard.controller;

import com.revature.studyforce.flashcard.dto.RatingDTO;
import com.revature.studyforce.flashcard.dto.RatingResponseDTO;
import com.revature.studyforce.flashcard.dto.VoteDTO;
import com.revature.studyforce.flashcard.model.Rating;
import com.revature.studyforce.flashcard.service.FlashcardService;
import com.revature.studyforce.flashcard.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for rating resource handling using {@link RatingService}
 * @author Edson Rodriguez
 */
@RestController
@CrossOrigin
@RequestMapping("/flashcards/ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService){
        this.ratingService =ratingService;
    }

    /**
     * POST request for 'createRating' in RatingService {@link RatingService#createRating(RatingDTO)}
     * @param rating the data transfer object that contains the flashcardId, userId and difficulty rating to save the new rating record
     * @return A data transfer object that represents the newly created rating object
     */
    @PostMapping
    public RatingResponseDTO createRating(@RequestBody RatingDTO rating ){
        return ratingService.createRating(rating);
    }

    /**
     * GET request for 'getRating' in RatingService {@link RatingService#getRating(int, int)}
     * @param flashcardId The flashcard id you want to get the rating from
     * @param userId The user id of the user who submitted the rating
     * @return The ratingDTO if there is a match, BAD.REQUEST if it wasn't found.
     */
    @GetMapping
    public RatingDTO getRating(@RequestParam int flashcardId, @RequestParam int userId){
        return ratingService.getRating(flashcardId,userId);
    }

    /**
     * GET request for 'getAllRatings' in RatingService {@link RatingService#getAllRatings(int)}
     * @param flashcardId - The flashcard id you want to get the ratings for
     * @return - A List of ratingDTOs if there is a match, BAD.REQUEST if it wasn't found.
     */
    @GetMapping("/all")
    public List<Rating> getAllRatings(@RequestParam int flashcardId){
        return ratingService.getAllRatings(flashcardId);
    }

    // (may use in the future)
//    /**
//     * Deletes existing rating with given id {@link RatingService#delete(int)}
//     * @param id - Rating to be deleted from the database
//     * @return - returns deletion success boolean
//     */
//    @DeleteMapping("/{id}")
//    public @ResponseBody Boolean delete(@PathVariable("id") Integer id) {
//        ratingService.delete(id);
//        return true;
//    }

}
