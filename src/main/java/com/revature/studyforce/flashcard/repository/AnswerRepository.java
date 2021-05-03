package com.revature.studyforce.flashcard.repository;

import com.revature.studyforce.flashcard.model.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    Page<Answer> findByFlashcardId(int id, PageRequest of);
}
