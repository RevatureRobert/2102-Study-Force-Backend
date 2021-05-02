package com.revature.studyforce.user.contollers;
import com.revature.studyforce.user.dto.UserDTO;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
/**
 * User Controller
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
    @GetMapping("/user/{userId}")
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
    @GetMapping("/name")
    public Page<UserDTO> getUserByFirstName(@RequestParam(name = "name") String name,
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
    @GetMapping("/email")
    public UserDTO getUserByEmail(@RequestParam(name = "email") String email){
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
    @GetMapping("/timeStamp")
    public Page<UserDTO> getUserByCreationTime(@RequestParam("time") Timestamp timestamp,
                                        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                        @RequestParam(value = "offset", required = false, defaultValue = "10") int offset,
                                        @RequestParam(value = "sortby", required = false, defaultValue = "userId") String sortBy,
                                        @RequestParam(value = "order", required = false, defaultValue = "ASC") String order){
        return userService.getUserByCreationTime(timestamp, page,offset,sortBy,order);
    }

}
