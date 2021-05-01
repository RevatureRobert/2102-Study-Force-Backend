package com.revature.studyforce.flashcard.repository;

import com.revature.studyforce.flashcard.model.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents the repository for the Quiz model {@link Quiz}
 * @author Edson Rodriguez
 * @author Nick Zimmerman
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer> {

    Page<Quiz> getQuizzesByUserId(final int user_id);

}
