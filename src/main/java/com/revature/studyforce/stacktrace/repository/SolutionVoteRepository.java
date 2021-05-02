package com.revature.StudyForce.stacktrace.repository;

import com.revature.StudyForce.stacktrace.model.Solution;
import com.revature.StudyForce.stacktrace.model.SolutionVote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Basic repository for {@link SolutionVote}
 * @author Joey Elmblad
 */
public interface SolutionVoteRepository extends JpaRepository<SolutionVote, Integer> {
}
