package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.dto.RatingDTO;
import com.revature.studyforce.flashcard.dto.RatingResponseDTO;
import com.revature.studyforce.flashcard.dto.VoteDTO;
import com.revature.studyforce.flashcard.model.Difficulty;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Rating;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.repository.RatingRepository;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Optional<Rating> optRating = ratingRepository.findByFlashcard_idAndUser_userId(optFlashcard.get().getId(),optUser.get().getUserId());
        if(optRating.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User already rated this flashcard");

        ratingRepository.save(new Rating(0,optFlashcard.get(),optUser.get(), difficulty));
        List<Rating> ratings = ratingRepository.findByFlashcardId(ratingDTO.getFlashcardId());

        double sum = 0;
        for(Rating rating : ratings){
            sum += rating.getRatingValue().difficultyValue;
        }
        return new RatingResponseDTO(ratings.size(),(Math.round(sum/ratings.size())));
    }

    /**
     * Method used to retrieve a rating made by a user on a specific flashcard {@link Rating}
     * @param flashcardId The id of the flashcard to look for
     * @param userId The user id that made the rating
     * @return The RatingDTO {@link RatingDTO} with the rating information of the user on the flashcard or a bad request of the user hasn't rated that flashcard
     */
    public RatingDTO getRating(int flashcardId, int userId){
        Optional<Flashcard> optFlashcard = flashcardRepository.findById(flashcardId);
        Optional<User> optUser = userRepository.findById(userId);

        if(!optFlashcard.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Flashcard not found exception");
        if(!optUser.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found exception");

        Optional<Rating> optRating = ratingRepository.findByFlashcard_idAndUser_userId(optFlashcard.get().getId(),optUser.get().getUserId());
        if(optRating.isPresent())
            return RatingDTO.ratingToDTO().apply(optRating.get());
        else
            throw new ResponseStatusException(HttpStatus.GONE,"User rating not found for this flashcard");
    }

    /**
     * Retrieves a List of all Ratings for the given Flashcard
     * @param flashcardId - the flashcard for which to retrieve all ratings
     * @return - returns a List of Ratings according the the flashcardId
     */
    public List<Rating> getAllRatings(int flashcardId){
        Optional<Flashcard> flashcard = flashcardRepository.findById(flashcardId);
        if (!flashcard.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flashcard not found exception");
        }

        return ratingRepository.findByFlashcardId(flashcard.get().getId());
    }

    // (may use in the future)
//    /**
//     * delete() method mapped to HTTP DELETE requests
//     * @param id - Rating id to be deleted from the database
//     */
//    public void delete(int id) {
//        if (id > 0) {
//            Rating original = ratingRepository.findById(id).get();
//            Flashcard flashcard = flashcardRepository.findById(1000000).get();
//            User user = userRepository.findById(1000000).get();
////            user = userRepository.save(user);
////            flashcard = flashcardRepository.save(flashcard);
//            original.setUser(user);
//            original.setFlashcard(flashcard);
//            original = ratingRepository.save(original);
//      System.out.println("ORIGINAL***************************: " + original);
//        }
//    }
}
