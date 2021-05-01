package com.revature.studyforce.user.repository;

import com.revature.studyforce.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * Repository for Basic repository for {@link User}
 * @author Lok Kan Kung
 * @author Daniel Reyes
 */

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
    Page<User> findByNameIgnoreCase(String name, Pageable pageable);
    Page<User> findByRegistrationTimeAfter(Timestamp creation, Pageable pageable);
}