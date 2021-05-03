package com.revature.studyforce.stacktrace.repository;

import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.user.model.Batch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Basic repo for  {@link Stacktrace}
 * author: Noel Shaji
 */

public interface StacktraceRepository extends JpaRepository<Stacktrace, Integer> {
    Page<Stacktrace> findByTechnologyId_technologyId(int technologyId, Pageable pageable);
}
