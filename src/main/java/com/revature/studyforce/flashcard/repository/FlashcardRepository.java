package com.revature.studyforce.flashcard.repository;

import com.revature.studyforce.flashcard.model.Flashcard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Flashcard model {@link Flashcard}
 * @author Luke Mohr
 */
@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {

    Page<Flashcard> findAllByQuestionDifficultyTotal(int questionDifficultyTotal, Pageable pageable);

    Page<Flashcard> findAllByIsResolved(boolean resolved, Pageable pageable);

    Page<Flashcard> findAllByTopicTopicName(String topicName, Pageable pageable);
}
