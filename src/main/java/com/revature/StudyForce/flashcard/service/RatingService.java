package com.revature.StudyForce.flashcard.service;

import com.revature.StudyForce.flashcard.dto.RatingDTO;
import com.revature.StudyForce.flashcard.dto.RatingResponseDTO;
import com.revature.StudyForce.flashcard.model.Rating;
import com.revature.StudyForce.flashcard.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Services for the Rating repository {@link RatingRepository}
 * @author Edson Rodriguez
 */
@Service
public class RatingService {
    private final RatingRepository RATING_REPO;

    /**
     * Constructor used to instantiate the repository
     * @param ratingRepository  The repository used throughout the RatingService
     */
    @Autowired
    public RatingService(RatingRepository ratingRepository){
        this.RATING_REPO = ratingRepository;
    }

    /**
     * Method to save a new rating
     */
    //TODO: change the return type
    public RatingResponseDTO createRating(RatingDTO ratingDTO){
        return null;
        //return RATING_REPO.save();
    }


}
