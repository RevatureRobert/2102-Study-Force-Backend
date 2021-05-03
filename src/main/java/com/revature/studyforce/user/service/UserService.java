package com.revature.studyforce.user.service;

import com.revature.studyforce.user.dto.UserDTO;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Locale;
import java.util.Optional;

/**
 * Service Layer
 * @author Daniel Reyes
 */

@Service
public class UserService {


    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * GET ALL USERS
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Users in database
     */
    public Page<UserDTO> getAllUsers(int page, int offset, String sortBy, String order){
        Page<User> users;
        users = userRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        return users.map(UserDTO.userToDTO());
    }

    /**
     * Get User by their Id
     * @param id belonging to user
     * @return user with that userId
     */
    public UserDTO getUserById(int id){
        Optional<User> user = userRepository.findById(id);
        return user.map(userMap -> UserDTO.userToDTO().apply(userMap)).orElse(null);
    }

    /**
     * Get User with matching email
     * @param email belonging to user
     * @return user
     */
    public UserDTO getUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(userMap -> UserDTO.userToDTO().apply(userMap)).orElse(null);
    }

    /**
     * GET ALL USERS Matching a name
     * @param name name to compare
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Users in database with a matching name
     */
    public Page<UserDTO> getUserByName(String name, int page, int offset, String sortBy, String order){
        Page<User> users;
        users = userRepository.findByNameIgnoreCase(name, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        return users.map(UserDTO.userToDTO());
    }

    /**
     * GET ALL USERS who registered after a given date
     * @param timestamp timestamp to check
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Users in database who registered after a specific date
     */
    public Page<UserDTO> getUserByCreationTime(Long timestamp, int page, int offset, String sortBy, String order){
        Timestamp t = Timestamp.from(Instant.ofEpochMilli(timestamp));
        Page<User> users;
        users = userRepository.findByRegistrationTimeAfter(t, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        return users.map(UserDTO.userToDTO());
    }


}
