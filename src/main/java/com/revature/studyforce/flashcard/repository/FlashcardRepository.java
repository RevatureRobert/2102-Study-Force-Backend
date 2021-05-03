package com.revature.studyforce.flashcard.repository;

import com.revature.studyforce.flashcard.model.Flashcard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring repository for Flashcards
 *
 * @author Luke
 */
@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {

    // Find questions by difficulty
    Page<Flashcard> findALlByQuestionDifficultyTotal(int questionDifficultyTotal, Pageable pageable);
}
