package com.revature.StudyForce.flashcard.repository;

import com.revature.StudyForce.flashcard.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Represents the repository for the Vote model {@link Vote}
 * @author Elizabeth Ye
 */
public interface VoteRepository extends JpaRepository<Vote, Integer> {
}
