package com.revature.StudyForce.user.repository;

import com.revature.StudyForce.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for Basic repository for {@link User}
 * @author Lok Kan Kung
 */
public interface UserRepository extends JpaRepository<User,Integer> {
}
