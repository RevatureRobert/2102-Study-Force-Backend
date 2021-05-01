package com.revature.StudyForce.flashcard.repository;

import com.revature.StudyForce.flashcard.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents the repository for the Quiz model {@link Quiz}
 * @author Edson Rodriguez
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer> {

}
