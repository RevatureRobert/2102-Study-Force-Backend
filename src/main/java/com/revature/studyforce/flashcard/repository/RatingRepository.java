package com.revature.studyforce.flashcard.repository;

import com.revature.studyforce.flashcard.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


/**
 * Represents the Repository for the Rating model {@link Rating}
 *
 * @author Edson Rodriguez
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer> {
    List<Rating> findByFlashcard_id(final int id);
}
