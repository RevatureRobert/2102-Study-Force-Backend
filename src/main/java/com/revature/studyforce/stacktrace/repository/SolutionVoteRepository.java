package com.revature.studyforce.stacktrace.repository;

import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.stacktrace.model.SolutionVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Basic repository for {@link SolutionVote}
 * @author Joey Elmblad
 */
public interface SolutionVoteRepository extends JpaRepository<SolutionVote, Integer> {
    @Modifying
    @Query("from SolutionVote where solution_id = :solutionId")
    List<SolutionVote> findBySolutionId(@Param("solutionId") int stacktraceId);

    @Transactional
    @Modifying
    @Query("delete from SolutionVote where solution_id = :solutionId")
    void deleteBySolutionId(@Param("solutionId") int solutionId);
}
