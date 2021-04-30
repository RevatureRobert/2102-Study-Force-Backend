package com.revature.StudyForce.user.controller;

import com.revature.StudyForce.user.dto.UserDTO;
import com.revature.StudyForce.user.model.User;
import com.revature.StudyForce.user.repository.UserRepository;
import com.revature.StudyForce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    /**
     * Put request used to update user details including: first name, last name, and password
     * @param user UserDTO request used to update a user's details
     * @return Return status code of 200 if successfully updated user details, else return a
     *          status code of 400 for bad request. Response body will return UserDTO if successful
     *
     *          TODO: change parameter to UserDTO
     * @author Joshua Swanson
     */
    @PutMapping
    public UserDTO updateUserDetails(@RequestBody UserDTO user){
       return userService.updateUserDetails(user);
    }
}
