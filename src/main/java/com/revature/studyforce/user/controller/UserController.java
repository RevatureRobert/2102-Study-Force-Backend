package com.revature.studyforce.user.controller;

import com.revature.studyforce.cognito.CognitoService;
import com.revature.studyforce.user.dto.*;
import com.revature.studyforce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

/**
 * User Controller for Users {@link UserService}
 * @author Daniel Reyes
 * @author Daniel Bernier
 * @author Nick Wickham
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CognitoService cognitoService;

    @Autowired
    public UserController(UserService userService, CognitoService cognitoService){
        this.userService = userService;
        this.cognitoService = cognitoService;
    }

    /**
     * GET mapping for '/getAllUsers' in {@link UserService}
     * @param sortBy field to be sorted by ["id" | "registration" | "email" | "authority" | "active" | "lastlogin"] case insensitive defaults to userId
     * @param order type of order to sort users [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 0
     * @param offset number of users displayed per page [5 | 10 | 25 | 50] defaults to 25 if invalid
     * @return page of Users dependent on provided page , offset, sort, and order
     */
    @GetMapping
    public Page<UserDTO> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "offset", required = false, defaultValue = "25") int offset,
                                     @RequestParam(value = "sort", required = false, defaultValue = "userId") String sortBy,
                                     @RequestParam(value = "order", required = false, defaultValue = "ASC") String order) {
        return userService.getAllUsers(page, offset, sortBy, order);
    }

    /**
     * GET request for 'getUserById' in {@link UserService#getUserById(int)}
     * @param id int input belonging to user
     * @return single user return that matches userId Param
     */
    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable(name = "userId") int id){
        return userService.getUserById(id);
    }

    /**
     * GET mapping for '/getUserByName' in {@link UserService#getUserByName(String, int, int, String, String)}
     * @param name name to compare
     * @param sortBy field to be sorted by ["id" | "registration" | "email" | "authority" | "active" | "lastlogin"]  case insensitive defaults to userId
     * @param order type of order to sort users [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 0
     * @param offset number of users displayed per page [5 | 10 | 25 | 50] defaults to 25 if invalid
     * @return page of Users dependent on provided page , offset, sort, and order
     */
    @GetMapping("/name")
    public Page<UserDTO> getUserByName(@RequestParam(name = "name") String name,
                                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(value = "offset", required = false, defaultValue = "25") int offset,
                                       @RequestParam(value = "sort", required = false, defaultValue = "userId") String sortBy,
                                       @RequestParam(value = "order", required = false, defaultValue = "ASC") String order){
        return userService.getUserByName(name, page,offset,sortBy,order);
    }

    /**
     * GET request for '/email' using {@link UserService#getUserByEmail(String)}
     * @param email belonging to user
     * @return single user with matching email
     */
    @GetMapping("/email")
    public UserDTO getUserByEmail(@RequestParam(name = "email") String email){
        return userService.getUserByEmail(email);
    }

    /**
     * GET request for '/search' using {@link UserService#getBySearch(String, int, int, String, String)}
     * @param search string to search name and email by
     * @param sortBy field to be sorted by ["id" | "registration" | "email" | "authority" | "active" | "lastlogin"]  case insensitive defaults to userId
     * @param order type of order to sort users [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 0
     * @param offset number of users displayed per page [5 | 10 | 25 | 50] defaults to 25 if invalid
     * @return A page of data transfer representation of users searched by
     */
    @GetMapping("/search")
    public Page<UserDTO> getBySearch(@RequestParam(name = "search") String search,
                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "offset", required = false, defaultValue = "25") int offset,
                                     @RequestParam(value = "sort", required = false, defaultValue = "userId") String sortBy,
                                     @RequestParam(value = "order", required = false, defaultValue = "ASC") String order){
        return userService.getBySearch(search, page, offset, sortBy, order);
    }

    /**
     * GET request getting user based on security context {@link com.revature.studyforce.cognito.CognitoAccessTokenConverter}
     * @param principal user found in access token.
     * @return single user with matching email
     */
    @GetMapping("/me")
    public UserDTO getUserByContext(Authentication principal){
        return userService.getUserByEmail(principal.getName());
    }

    /**
     * GET mapping for '/getUserByCreationTime' in {@link UserService#getUserByCreationTime(long, int, int, String, String)}
     * @param timestamp timestamp to check
     * @param sortBy field to be sorted by ["id" | "registration" | "email" | "authority" | "active" | "lastlogin"]  case insensitive defaults to userId
     * @param order type of order to sort users [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 0
     * @param offset number of users displayed per page [5 | 10 | 25 | 50] defaults to 25 if invalid
     * @return page of Users dependent on provided page , offset, sort, and order
     */
    @GetMapping("/time/{epochMilliseconds}")
    public Page<UserDTO> getUserByCreationTime(@PathVariable("epochMilliseconds") long timestamp,
                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam(value = "offset", required = false, defaultValue = "25") int offset,
                                        @RequestParam(value = "sort", required = false, defaultValue = "userId") String sortBy,
                                        @RequestParam(value = "order", required = false, defaultValue = "ASC") String order){
        return userService.getUserByCreationTime(timestamp, page,offset,sortBy,order);
    }
    @PostMapping("/bulk")@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})@ResponseStatus(HttpStatus.CREATED)
    public String bulkCreateUsers(@RequestBody List<BulkCreateUsersDTO> usersFromCsv) throws IOException, InterruptedException {
        return cognitoService.bulkCreateUsers(usersFromCsv);
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
    @PutMapping("/authority")@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    public UserDTO updateUserAuthority(@RequestBody UserAuthorityDTO userAuthorityDTO){
        return userService.updateUserAuthority(userAuthorityDTO);
    }

    /**
     * Processes PUT request on '/active' to update the active status of a user utilizing {@link UserService#updateUserIsActive(UserIsActiveDTO)}
     * @param userIsActiveDTO A data transfer object containing the user's id and their new active status
     * @return The data transfer representation of the updated user
     */
    @PutMapping("/active")@Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    public UserDTO updateUserIsActive(@RequestBody UserIsActiveDTO userIsActiveDTO){
        return userService.updateUserIsActive(userIsActiveDTO);
    }

    /**
     * Processes PUT request on '/subscription' to update the subscription statuses of a user utilizing {@link UserService#updateUserSubscriptionStatus(UserSubscriptionsDTO)}
     * @param userSubscriptionsDTO A data transfer object containing the user's id and their new subscription statuses
     * @return The data transfer representation of the updated user
     */
    @PutMapping("/subscription") @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    public UserDTO updateUserSubscriptionStatus (@RequestBody UserSubscriptionsDTO userSubscriptionsDTO){
        return userService.updateUserSubscriptionStatus(userSubscriptionsDTO);
    }
}
