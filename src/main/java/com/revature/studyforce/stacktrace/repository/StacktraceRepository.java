package com.revature.studyforce.stacktrace.repository;

import com.revature.studyforce.stacktrace.model.Stacktrace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Basic repo for  {@link com.revature.studyforce.stacktrace.model.Stacktrace}
 * author: Noel Shaji
 */
@Repository
public interface StacktraceRepository extends JpaRepository<Stacktrace, Integer> {
    Page<Stacktrace> findByTechnologyId_technologyId(int technologyId, Pageable pageable);
}
