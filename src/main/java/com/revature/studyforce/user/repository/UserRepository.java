package com.revature.studyforce.user.repository;

import com.revature.studyforce.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for User
 * @author Lok Kan Kung
 */
public interface UserRepository extends JpaRepository<User,Integer> {
}