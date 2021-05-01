package com.revature.StudyForce.flashcard.repository;

import com.revature.StudyForce.flashcard.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepo extends JpaRepository<Answer,Integer> {
}
