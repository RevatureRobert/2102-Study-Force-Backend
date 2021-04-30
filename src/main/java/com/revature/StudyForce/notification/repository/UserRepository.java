package com.revature.StudyForce.notification.repository;

import com.revature.StudyForce.notification.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Integer> {
}
