package com.revature.studyforce.user.service;

import com.revature.studyforce.user.dto.UserDTO;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import com.revature.studyforce.user.util.hashing.PasswordHashingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    private final UserRepository USER_REPO;

//    private final PasswordHashingUtil PASSWORD_HASHING_UTIL;


    @Autowired
    public UserService(UserRepository userRepo, PasswordHashingUtil passwordHashingUtil) {
        this.USER_REPO = userRepo;
//        this.PASSWORD_HASHING_UTIL = passwordHashingUtil;
    }



    /**
     * Persists a user object by calling {@link UserRepository#save(Object)} and returns the newly saved user object as its data transfer representation
     * @param user The User object being persisted
     * @return The newly persisted user object converted to its data transfer representation using {@link UserDTO#userToDTO()}
     */
    public UserDTO createNewUser(User user) {
//        try {
//            user.setPassword(PASSWORD_HASHING_UTIL.hashPasswordWithEmail(user.getEmail(), user.getPassword()));
//        } catch (NoSuchAlgorithmException ignored) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR );
//        }
        User saved = USER_REPO.save(user);
        return UserDTO.userToDTO().apply(saved);
    }
}
