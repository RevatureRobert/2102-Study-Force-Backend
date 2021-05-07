package com.revature.studyforce.flashcard.repository;

import com.revature.studyforce.flashcard.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents the repository for the Quiz model {@link Quiz}
 * @author Edson Rodriguez
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer> {

}
