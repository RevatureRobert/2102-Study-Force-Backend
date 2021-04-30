package com.revature.StudyForce.user.service;

import com.revature.StudyForce.user.exception.MalformedUserDetailsException;
import com.revature.StudyForce.user.exception.NoUserFoundByIdException;
import com.revature.StudyForce.user.model.User;
import com.revature.StudyForce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * Updates a user's first name, last name and password passed from request body. Read user
     *  from data store, update columns and save the user to persist new user details.
     * @param updatedUser UserDTO containing updated user information that needs to persisted
     * @return  Returns UserDTO if successfully updating data store
     *
     * @author Joshua Swanson
     */
    // TODO: replace this implementation with DTO
    public User updateUserDetails(User updatedUser){
        User repoUser = userRepository.getOne(updatedUser.getUserId());

        if(repoUser == null){
            throw new NoUserFoundByIdException(updatedUser.getUserId());
        }

        repoUser.setFirstName(repoUser.getFirstName());
        repoUser.setLastName(updatedUser.getLastName());
        repoUser.setPassword(updatedUser.getPassword());

        try{
            return userRepository.save(repoUser);
        }catch(IllegalArgumentException e){
            throw new MalformedUserDetailsException("User details malformed");
        }
    }
}
