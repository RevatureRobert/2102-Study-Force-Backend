package com.revature.studyforce.user.service;

import com.revature.studyforce.user.dto.UserDTO;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Persists a user object by calling {@link UserRepository#save(Object)} and returns the newly saved user object as its data transfer representation
     * @param user The User object being persisted
     * @return The newly persisted user object converted to its data transfer representation using {@link UserDTO#userToDTO()}
     */
    public UserDTO createNewUser(User user) {
        User saved = userRepo.save(user);
        return UserDTO.userToDTO().apply(saved);
    }
}
