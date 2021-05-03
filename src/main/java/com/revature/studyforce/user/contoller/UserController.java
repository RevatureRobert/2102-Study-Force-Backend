package com.revature.studyforce.user.contoller;

import com.revature.studyforce.user.dto.UserDTO;
import com.revature.studyforce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * User Controller for Users {@link UserService}
 * @author Daniel Reyes
 */


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/User")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * GET mapping for '/getAllUsers' in {@link UserService}
     * @param sortBy field to be sorted by [batchId | creationTime | name] case insensitive defaults to batchId
     * @param order type of order to sort batches [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 5
     * @param offset number of batches displayed per page [5/ 10/ 25/ 50] defaults to 5 if invalid
     * @return page of Users dependent on provided page , offset, sort, and order
     */
    @GetMapping("/all")
    public Page<UserDTO> getAllUsers(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                     @RequestParam(value = "offset", required = false, defaultValue = "10") int offset,
                                     @RequestParam(value = "sort", required = false, defaultValue = "userId") String sortBy,
                                     @RequestParam(value = "order", required = false, defaultValue = "ASC") String order) {
        return userService.getAllUsers(page, offset, sortBy, order);
    }

    /**
     * GET request for 'getUserById' in {@link UserService#getUserById(int)}
     * @param id int input belonging to user
     * @return single user return that matches userId Param
     */
    @GetMapping("/user/{userId}")
    public UserDTO getUserById(@PathVariable(name = "userId") int id){
        return userService.getUserById(id);
    }

    /**
     * GET mapping for '/getUserByName' in {@link UserService#getUserByName(String, int, int, String, String)}
     * @param name name to compare
     * @param sortBy field to be sorted by [batchId | creationTime | name] case insensitive defaults to batchId
     * @param order type of order to sort batches [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 5
     * @param offset number of batches displayed per page [5/ 10/ 25/ 50] defaults to 5 if invalid
     * @return page of Users dependent on provided page , offset, sort, and order
     */
    @GetMapping("/name")
    public Page<UserDTO> getUserByName(@RequestParam(name = "name") String name,
                                       @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(value = "offset", required = false, defaultValue = "10") int offset,
                                       @RequestParam(value = "sort", required = false, defaultValue = "userId") String sortBy,
                                       @RequestParam(value = "order", required = false, defaultValue = "ASC") String order){
        return userService.getUserByName(name, page,offset,sortBy,order);
    }

    /**
     * GET request for 'getBatchByEmail' in {@link UserService#getUserByEmail(String)}
     * @param email belonging to user
     * @return single user with matching email
     */
    @GetMapping("/email")
    public UserDTO getUserByEmail(@RequestParam(name = "email") String email){
        return userService.getUserByEmail(email);
    }

    /**
     * GET mapping for '/getUserByCreationTime' in {@link UserService#getUserByCreationTime(long, int, int, String, String)}
     * @param timestamp timestamp to check
     * @param sortBy field to be sorted by [batchId | creationTime | name] case insensitive defaults to batchId
     * @param order type of order to sort batches [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 10
     * @param offset number of batches displayed per page [10/ 15/ 25/ 50] defaults to 5 if invalid
     * @return page of Users dependent on provided page , offset, sort, and order
     */
    @GetMapping("/user/time")
    public Page<UserDTO> getUserByCreationTime(@RequestParam("time") long timestamp,
                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam(value = "offset", required = false, defaultValue = "10") int offset,
                                        @RequestParam(value = "sort", required = false, defaultValue = "userId") String sortBy,
                                        @RequestParam(value = "order", required = false, defaultValue = "ASC") String order){
        return userService.getUserByCreationTime(timestamp, page,offset,sortBy,order);
    }

}
