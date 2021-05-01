package com.revature.studyforce.flashcard.repository;

import com.revature.studyforce.flashcard.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Represents the Repository for the Rating model
 *
 * @author Edson Rodriguez
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer> {

}
