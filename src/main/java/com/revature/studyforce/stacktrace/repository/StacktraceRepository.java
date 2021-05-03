package com.revature.StudyForce.stacktrace.repository;

import com.revature.StudyForce.stacktrace.model.Stacktrace;
import com.revature.StudyForce.user.model.Batch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Basic repo for  {@link com.revature.StudyForce.stacktrace.model.Stacktrace}
 * author: Noel Shaji
 */

public interface StacktraceRepository extends JpaRepository<Stacktrace, Integer> {
    Page<Stacktrace> findByTechnologyId_technologyId(int technologyId, Pageable pageable);
}
