package com.revature.studyforce.user.controller;

import com.revature.studyforce.user.dto.UserDTO;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user endpoints
 * @author Steven Ceglarek
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService USER_SERVICE;

    @Autowired
    public UserController(UserService userService) { this.USER_SERVICE = userService;}



    /**
     * Adds a new user object
     * @param userDTO The user to be added as a data transfer object
     * @return The data transfer representation of the newly added user object
     */
    @PostMapping("/create")
    public UserDTO addUser(@RequestBody UserDTO userDTO) {
        userDTO.setUserId(0);
        User u = UserDTO.dtoToUser().apply(userDTO);
    return USER_SERVICE.createNewUser(u);
    }





}
