package com.revature.studyforce.user.repository;

import com.revature.studyforce.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for Basic repository for {@link User}
 * @author Lok Kan Kung
 */
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
