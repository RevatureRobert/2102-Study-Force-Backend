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
    Page<Flashcard> findAllByQuestionDifficultyTotal(int questionDifficultyTotal, Pageable pageable);

    // Find questions by topic
    Page<Flashcard> findAllByTopicTopicName(String topicName, Pageable pageable);

    // Find questions by resolved status
    Page<Flashcard> findAllByIsResolved(Boolean isResolved, Pageable pageable);
}
