package com.revature.StudyForce.repository;

import com.revature.StudyForce.model.Flashcard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlashcardRepo extends JpaRepository<Flashcard, Integer> {

    List<Flashcard> findALlByQuestionDifficultyTotal(int questionDifficultyTotal, Pageable pageable);
}
