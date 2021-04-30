package com.revature.StudyForce.notification.repository;

import com.revature.StudyForce.notification.model.FeatureArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "featureAreas", path = "feature-areas")
public interface FeatureAreaRepository extends JpaRepository<FeatureArea, Integer> {
}
