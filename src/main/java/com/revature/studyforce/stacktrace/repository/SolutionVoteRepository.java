package com.revature.studyforce.stacktrace.repository;

import com.revature.studyforce.stacktrace.model.SolutionVote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Basic repository for {@link SolutionVote}
 * @author Joey Elmblad
 */
public interface SolutionVoteRepository extends JpaRepository<SolutionVote, Integer> {
}
