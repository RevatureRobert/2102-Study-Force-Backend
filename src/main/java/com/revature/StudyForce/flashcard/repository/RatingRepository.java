package com.revature.StudyForce.flashcard.repository;

import com.revature.StudyForce.flashcard.model.Rating;
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
