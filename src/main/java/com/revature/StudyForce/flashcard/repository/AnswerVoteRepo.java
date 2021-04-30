package com.revature.StudyForce.flashcard.repository;

import com.revature.StudyForce.flashcard.dto.AnswerVoteDTO;
import com.revature.StudyForce.flashcard.model.AnswerVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerVoteRepo extends JpaRepository<AnswerVote, Integer> {
}
