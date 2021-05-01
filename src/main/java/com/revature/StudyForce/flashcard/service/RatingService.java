package com.revature.StudyForce.flashcard.service;

import com.revature.StudyForce.flashcard.dto.RatingDTO;
import com.revature.StudyForce.flashcard.dto.RatingResponseDTO;
import com.revature.StudyForce.flashcard.model.Difficulty;
import com.revature.StudyForce.flashcard.model.Flashcard;
import com.revature.StudyForce.flashcard.model.Rating;
import com.revature.StudyForce.flashcard.repository.FlashcardRepository;
import com.revature.StudyForce.flashcard.repository.RatingRepository;
import com.revature.StudyForce.user.model.User;
import com.revature.StudyForce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

/**
 * Services for the Rating repository {@link RatingRepository}
 * @author Edson Rodriguez
 */
@Service
public class RatingService {
    private final RatingRepository RATING_REPO;
    private final FlashcardRepository FLASHCARD_REPO;
    private final UserRepository USER_REPO;

    /**
     * Constructor used to instantiate the necessary repository's
     * @param ratingRepository {@link RatingRepository}
     * @param flashcardRepository {@link FlashcardRepository}
     * @param userRepository {@link UserRepository}
     */
    @Autowired
    public RatingService(RatingRepository ratingRepository,FlashcardRepository flashcardRepository,UserRepository userRepository){

        this.RATING_REPO = ratingRepository;
        this.FLASHCARD_REPO = flashcardRepository;
        this.USER_REPO =userRepository;
    }

    /**
     * Method used to store a rating
     * @param ratingDTO The data transfer object that represents an incoming request to store a rating
     * @return A data transfer object with the new rating score and the amount of ratings for that flashcard
     */
    public RatingResponseDTO createRating(RatingDTO ratingDTO){
        Optional<Flashcard> optFlashcard = FLASHCARD_REPO.findById(ratingDTO.getFlashcardId());
        Optional<User> optUser = USER_REPO.findById(ratingDTO.getUserId());
        Difficulty difficulty = Difficulty.fromInteger(ratingDTO.getRatingScore());

        if(!optFlashcard.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Flashcard not found exception");
        if(!optUser.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found exception");
        if(difficulty==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Difficulty must be a number from 1 to 3");

        RATING_REPO.save(new Rating(0,optFlashcard.get(),optUser.get(), difficulty));
        List<Rating> ratings = RATING_REPO.findByFlashcard_id(ratingDTO.getFlashcardId());

        int sum = 0;
        for(Rating rating : ratings){
            sum += rating.getRatingValue().ordinal();
        }
        return new RatingResponseDTO(ratings.size(),(sum/ratings.size()));
    }


}
