package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.model.Rating;
import com.revature.studyforce.flashcard.repository.RatingRepository;
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
    public Rating createRating(Rating rating){
        return RATING_REPO.save(rating);
    }


}
