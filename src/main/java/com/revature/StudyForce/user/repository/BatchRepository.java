package com.revature.StudyForce.user.repository;

import com.revature.StudyForce.user.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Basic repository for {@link Batch}
 * @author Daniel Bernier
 */
public interface BatchRepository extends JpaRepository<Batch,Integer> {
}
