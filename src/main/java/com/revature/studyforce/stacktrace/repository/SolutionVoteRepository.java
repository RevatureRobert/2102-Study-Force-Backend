package com.revature.studyforce.stacktrace.repository;

import com.revature.studyforce.stacktrace.model.SolutionVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

//import javax.transaction.Transactional;
import java.util.List;

/**
 * Basic repository for {@link SolutionVote}
 * @author Joey Elmblad
 */
@Repository
public interface SolutionVoteRepository extends JpaRepository<SolutionVote, Integer> {
    @Modifying
    @Query(value = "select * from solution_vote where solution_id = :solutionId ;", nativeQuery = true)
    List<SolutionVote> findBySolutionId(@Param("solutionId") int solutionId);

    //@Transactional
    @Modifying
    @Query("delete from SolutionVote where solution_id = :solutionId")
    void deleteBySolutionId(@Param("solutionId") int solutionId);
}
