package com.revature.StudyForce.user.repository;

import com.revature.StudyForce.stacktrace.model.Stacktrace;
import com.revature.StudyForce.user.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Basic repo for  {@link com.revature.StudyForce.stacktrace.model.Stacktrace}
 * author: Noel Shaji
 */

public interface StacktraceRepository extends JpaRepository<Stacktrace, Integer> {

}
