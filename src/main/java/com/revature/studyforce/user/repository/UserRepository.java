package com.revature.studyforce.user.repository;

import com.revature.studyforce.stacktrace.model.Solution;
import com.revature.studyforce.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository for Basic repository for {@link User}
 * @author Lok Kan Kung
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
