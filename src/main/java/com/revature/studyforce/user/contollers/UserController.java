package com.revature.studyforce.user.contollers;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * User Controller
 * @author Daniel Reyes
 */


@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }
//
    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
//
//    @GetMapping("/{id}")
//    public User getUser(@PathVariable("id") String id){
//        return userService.getUserById(Integer.parseInt(id));
//    }
//
//    @GetMapping("/{firstName}")
//    public  User getUserByFirstName(@PathVariable("firstName") String firstName){
//        return userService.getUserByFirstName(firstName);
//    }
//
//    @GetMapping("/{lastName}")
//    public  User getUserByLastName(@PathVariable("lastName") String lastName){
//        return userService.getUserByLastName(lastName);
//    }
//
//    @GetMapping("/{email}")
//    public  User getUserByEmail(@PathVariable("email") String email){
//        return userService.getUserByEmail(email);
//    }

//    @GetMapping("/{timeStamp}")
//    public  User getUserByEmail(@PathVariable("time") Timestamp timestamp){
//        return userService.getUserByCreationTime(timestamp);
//    }

}
