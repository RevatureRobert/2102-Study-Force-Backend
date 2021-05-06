package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.repository.RatingRepository;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import com.revature.studyforce.flashcard.dto.RatingDTO;
import com.revature.studyforce.flashcard.dto.RatingResponseDTO;
import com.revature.studyforce.flashcard.model.Difficulty;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Rating;
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
    private final RatingRepository ratingRepository;
    private final FlashcardRepository flashcardRepository;
    private final UserRepository userRepository;

    /**
     * Constructor used to instantiate the necessary repository's
     * @param ratingRepository {@link RatingRepository}
     * @param flashcardRepo {@link FlashcardRepository}
     * @param userRepository {@link UserRepository}
     */
    @Autowired
    public RatingService(RatingRepository ratingRepository,FlashcardRepository flashcardRepo,UserRepository userRepository){
        this.ratingRepository = ratingRepository;
        this.flashcardRepository = flashcardRepo;
        this.userRepository =userRepository;
    }

    /**
     * Method used to store a rating
     * @param ratingDTO The data transfer object that represents an incoming request to store a rating
     * @return A data transfer object with the new rating score and the amount of ratings for that flashcard
     */
    public RatingResponseDTO createRating(RatingDTO ratingDTO){
        Optional<Flashcard> optFlashcard = flashcardRepository.findById(ratingDTO.getFlashcardId());
        Optional<User> optUser = userRepository.findById(ratingDTO.getUserId());
        Difficulty difficulty = Difficulty.fromInteger(ratingDTO.getRatingScore());

        if(!optFlashcard.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Flashcard not found exception");
        if(!optUser.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found exception");
        if(difficulty==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Difficulty must be a number from 1 to 3");

        ratingRepository.save(new Rating(0,optFlashcard.get(),optUser.get(), difficulty));
        List<Rating> ratings = ratingRepository.findByFlashcard_id(ratingDTO.getFlashcardId());

        int sum = 0;
        for(Rating rating : ratings){
            sum += rating.getRatingValue().difficultyValue;
        }
        return new RatingResponseDTO(ratings.size(),(sum/ratings.size()));
    }


}
