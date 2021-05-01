package com.revature.StudyForce.flashcard.repository;

import com.revature.StudyForce.flashcard.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepo extends JpaRepository<Vote, Integer> {
}
