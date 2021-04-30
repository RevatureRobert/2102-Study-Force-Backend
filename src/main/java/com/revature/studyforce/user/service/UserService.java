package com.revature.studyforce.user.service;

import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Timestamp;
import java.util.List;

/**
 * Service Layer
 * @author Daniel Reyes
 */

@Service
public class UserService {


    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Integer id){
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> getUserByFirstName(String firstName){
        return userRepository.findByFirstNameIgnoreCase(firstName);
    }
    public List<User> getUserByLastName(String lastName){
        return userRepository.findByLastNameIgnoreCase(lastName);
    }

    public List<User> getUserByCreationTime(Timestamp creation){
        return userRepository.findByRegistrationTime(creation);
    }

}
