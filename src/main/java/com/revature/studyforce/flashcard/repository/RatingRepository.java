package com.revature.studyforce.flashcard.repository;

import com.revature.studyforce.flashcard.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


/**
 * Represents the Repository for the Rating model {@link Rating}
 *
 * @author Edson Rodriguez
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer> {
    List<Rating> findByFlashcardId(final int id);
    Optional<Rating> findByFlashcard_idAndUser_userId(final int fId, final int uId);
}
