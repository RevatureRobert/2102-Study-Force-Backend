package com.revature.studyforce.stacktrace.repository;


import com.revature.studyforce.stacktrace.model.Solution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Basic repository for {@link Solution}
 * @author Joey Elmblad
 * @author Joshua Swanson
 */
@Repository
public interface SolutionRepository extends JpaRepository<Solution,Integer> {

    Page<Solution> findByStackTraceId_stacktraceId(int stacktraceId, Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Solution where solution_id = :solutionId")
    void deleteSolutionById(@Param("solutionId") int solutionId);

}
