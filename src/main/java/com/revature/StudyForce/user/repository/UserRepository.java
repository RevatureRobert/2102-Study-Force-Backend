package com.revature.StudyForce.user.repository;
import com.revature.StudyForce.user.model.Batch;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.revature.StudyForce.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.security.Timestamp;
import java.util.List;

/**
 * Repository for Basic repository for {@link User}
 * @author Lok Kan Kung
 * @author Daniel Reyes
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
    Batch findByRegistrationTime(Timestamp creation);






}
