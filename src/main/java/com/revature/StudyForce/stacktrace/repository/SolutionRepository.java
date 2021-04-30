package com.revature.StudyForce.stacktrace.repository;

import com.revature.StudyForce.stacktrace.model.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Basic repository for {@link Solution}
 * @author Joey Elmblad
 */
public interface SolutionRepository extends JpaRepository<Solution,Integer> {
}
