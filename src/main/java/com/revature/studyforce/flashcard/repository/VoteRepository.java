package com.revature.studyforce.flashcard.repository;

import com.revature.studyforce.flashcard.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Represents the repository for the Vote model {@link Vote}
 * @author Elizabeth Ye
 */
public interface VoteRepository extends JpaRepository<Vote, Integer> {
}
