package com.revature.studyforce.user.repository;

import com.revature.studyforce.user.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Basic repository for {@link Batch}
 * @author Daniel Bernier
 */
public interface BatchRepository extends JpaRepository<Batch,Integer> {
}
