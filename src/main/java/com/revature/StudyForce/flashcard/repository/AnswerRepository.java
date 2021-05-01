package com.revature.StudyForce.flashcard.repository;

import com.revature.StudyForce.flashcard.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Integer> {
}
