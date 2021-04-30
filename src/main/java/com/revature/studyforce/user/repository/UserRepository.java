package com.revature.studyforce.user.repository;

import com.revature.studyforce.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.security.Timestamp;
import java.util.List;

/**
 * Repository for Basic repository for {@link User}
 * @author Lok Kan Kung
 */
@RepositoryRestResource(collectionResourceRel = "Users", path = "user")
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    User findByFirstName(String firstName);
    User findByLastName(String lastName);
    //User findByCreationTime(Timestamp creation);

    /*
     * Find list of users with certain First Name or Last Name
     */
    List<User> findByFirstNameIgnoreCase(String firstName);
    List<User> findByLastNameIgnoreCase(String lastName);

    /*
     * Find By First Name or Last Name ignoring Upper or Lower case
     */
    User findByLastNameIgnoreCaseOrFirstNameIgnoreCase(String lastName, String firstName);

    /*
     * Find Users after certain time stamp
     */
    //List<User> findByregistrationTimeGreater(Timestamp timestamp);
    List<User> findByRegistrationTime(Timestamp creation);
}