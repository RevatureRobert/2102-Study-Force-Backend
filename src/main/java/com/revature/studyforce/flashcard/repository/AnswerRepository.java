package com.revature.studyforce.flashcard.repository;

import com.revature.studyforce.flashcard.model.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents the repository for the Answer model {@link Answer}
 * @author Edson Rodriguez
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    Page<Answer> findByFlashcard_id(int id, Pageable of);
}
