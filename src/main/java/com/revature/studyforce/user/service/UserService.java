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
     * @param id belonging to user
     * @return user with that userId
     */
    public UserDTO getUserById(int id){
        Optional<User> user = userRepository.findById(id);
        return user.map(userMap -> UserDTO.userToDTO().apply(userMap)).orElse(null);
    }

    /**
     * @param email belonging to user
     * @return user
     */
    public UserDTO getUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(userMap -> UserDTO.userToDTO().apply(userMap)).orElse(null);
    }

    /**
     * GET ALL USERS Matching a name
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
     * GET ALL USERS by Registration Day
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Users in database who registered after a specific date
     */
    public Page<UserDTO> getUserByCreationTime(Timestamp creation, int page, int offset, String sortBy, String order){
        Page<User> users;
        users = userRepository.findByRegistrationTimeAfter(creation, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        return users.map(UserDTO.userToDTO());
    }


}
