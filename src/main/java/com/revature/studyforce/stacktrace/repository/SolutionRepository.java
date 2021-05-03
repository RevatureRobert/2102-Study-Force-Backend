package com.revature.StudyForce.stacktrace.repository;

import com.revature.StudyForce.stacktrace.model.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Basic repository for {@link Solution}
 * @author Joey Elmblad
 */
public interface SolutionRepository extends JpaRepository<Solution,Integer> {
}
