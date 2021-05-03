package com.revature.studyforce.user.controller;

import com.revature.studyforce.user.dto.*;
import com.revature.studyforce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;

/**
 * User Controller
 * @author Daniel Reyes
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * GET ALL USERS
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Users in database
     */
    @GetMapping("/all")
    public Page<UserDTO> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "offset", required = false, defaultValue = "10") int offset,
                                     @RequestParam(value = "sortby", required = false, defaultValue = "userId") String sortBy,
                                     @RequestParam(value = "order", required = false, defaultValue = "ASC") String order) {
        return userService.getAllUsers(page, offset, sortBy, order);
    }

    /**
     * @param id belonging to user
     * @return user with that userId
     */
    @GetMapping("/{userId}")
    public UserDTO getUser(@PathVariable(name = "userId") int id){
        return userService.getUserById(id);
    }

    /**
     * GET ALL USERS Matching a name
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Users in database with a matching name
     */
    @GetMapping("/{name}")
    public Page<UserDTO> getUserByFirstName(@PathVariable(name = "name") String name,
                                            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                            @RequestParam(value = "offset", required = false, defaultValue = "10") int offset,
                                            @RequestParam(value = "sortby", required = false, defaultValue = "userId") String sortBy,
                                            @RequestParam(value = "order", required = false, defaultValue = "ASC") String order){
        return userService.getUserByName(name, page,offset,sortBy,order);
    }

    /**
     * @param email belonging to user
     * @return user
     */
    @GetMapping("/{email}")
    public UserDTO getUserByEmail(@PathVariable(name = "email") String email){
        return userService.getUserByEmail(email);
    }

    /**
     * GET ALL USERS by Registration Day
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Users in database who registered after a specific date
     */
    @GetMapping("/{timeStamp}")
    public Page<UserDTO> getUserByTime(@PathVariable("time") Timestamp timestamp,
                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam(value = "offset", required = false, defaultValue = "10") int offset,
                                        @RequestParam(value = "sortby", required = false, defaultValue = "userId") String sortBy,
                                        @RequestParam(value = "order", required = false, defaultValue = "ASC") String order){
        return userService.getUserByCreationTime(timestamp, page,offset,sortBy,order);
    }

    /**
     * Processes PUT request on '/name' to update the name of a user utilizing {@link UserService#updateUserName(UserNameDTO)}
     * @param userNameDTO A data transfer object containing the user's id and their new name
     * @return The data transfer representation of the updated user
     */
    @PutMapping("/name")
    public UserDTO updateUserName(@RequestBody UserNameDTO userNameDTO){
        return userService.updateUserName(userNameDTO);
    }

    /**
     * Processes PUT request on '/authority' to update the authority of a user utilizing {@link UserService#updateUserAuthority(UserAuthorityDTO)}
     * @param userAuthorityDTO A data transfer object containing the user's id and their new authority
     * @return The data transfer representation of the updated user
     */
    @PutMapping("/authority")
    public UserDTO updateUserAuthority(@RequestBody UserAuthorityDTO userAuthorityDTO){
        return userService.updateUserAuthority(userAuthorityDTO);
    }

    /**
     * Processes PUT request on '/active' to update the active status of a user utilizing {@link UserService#updateUserIsActive(UserIsActiveDTO)}
     * @param userIsActiveDTO A data transfer object containing the user's id and their new active status
     * @return The data transfer representation of the updated user
     */
    @PutMapping("/active")
    public UserDTO updateUserIsActive(@RequestBody UserIsActiveDTO userIsActiveDTO){
        return userService.updateUserIsActive(userIsActiveDTO);
    }

    /**
     * Processes PUT request on '/subscription' to update the subscription statuses of a user utilizing {@link UserService#updateUserSubscriptionStatus(UserSubscriptionsDTO)}
     * @param userSubscriptionsDTO A data transfer object containing the user's id and their new subscription statuses
     * @return The data transfer representation of the updated user
     */
    @PutMapping("/subscription")
    public UserDTO updateUserSubscriptionStatus (@RequestBody UserSubscriptionsDTO userSubscriptionsDTO){
        return userService.updateUserSubscriptionStatus(userSubscriptionsDTO);
    }
}
