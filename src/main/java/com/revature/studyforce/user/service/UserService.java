package com.revature.studyforce.user.service;

import com.revature.studyforce.user.dto.UserDTO;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Locale;
import java.util.Optional;

/**
 * Service Layer for Users {@link UserRepository}
 * @author Daniel Reyes
 */

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all Users with pagination from {@link UserRepository#findAll(Pageable)}
     * @param sortBy field to be sorted by ["id" | "registration" | "email" | "authority" | "active" | "lastlogin"] case insensitive defaults to userId
     * @param order type of order to sort users [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 0
     * @param offset number of Users displayed per page [5 | 10 | 25 | 50] defaults to 10 if invalid
     * @return page of Users dependent on provided page , offset, sort, and order
     */
    public Page<UserDTO> getAllUsers(int page, int offset, String sortBy, String order){

        page = pageValidation(page);
        sortBy = sortByValidation(sortBy);
        offset = offsetValidation(offset);

        Page<User> users;
        if(order.equalsIgnoreCase("DESC"))
            users = userRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        else
            users = userRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        return users.map(UserDTO.userToDTO());
    }

    /**
     * Retrieves User from {@link UserRepository
     * GET request for 'findById' in {@link UserRepository#findById(Object)}
     * @param id int input belonging to user
     * @return single user return that matches userId Param
     */
    public UserDTO getUserById(int id){
        Optional<User> user = userRepository.findById(id);
        return user.map(userMap -> UserDTO.userToDTO().apply(userMap)).orElse(null);
    }

    /**
     * Retrieves User from {@link UserRepository#findByEmail(String)}
     * @param email belonging to user
     * @return single user with matching email
     */
    public UserDTO getUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(userMap -> UserDTO.userToDTO().apply(userMap)).orElse(null);
    }

    /**
     * Retrieves all Users with pagination from {@link UserRepository#findByNameContainingIgnoreCase(String, Pageable)}
     * @param name name to compare
     * @param sortBy field to be sorted by ["id" | "registration" | "email" | "authority" | "active" | "lastlogin"] case insensitive defaults to userId
     * @param order type of order to sort users [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 0
     * @param offset number of Users displayed per page [5 | 10 | 25 | 50] defaults to 10 if invalid
     * @return page of Users dependent on provided page , offset, sort, and order
     */
    public Page<UserDTO> getUserByName(String name, int page, int offset, String sortBy, String order){
        page = pageValidation(page);
        sortBy = sortByValidation(sortBy);
        offset = offsetValidation(offset);

        Page<User> users;
        if(order.equalsIgnoreCase("DESC"))
            users = userRepository.findByNameContainingIgnoreCase(name, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        else
            users = userRepository.findByNameContainingIgnoreCase(name, PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        return users.map(UserDTO.userToDTO());
    }

    /**
     * Retrieves all Users with pagination from{@link UserRepository#findByRegistrationTimeAfter(Timestamp, Pageable)}
     * @param epochMilli timestamp to check
     * @param sortBy field to be sorted by ["id" | "registration" | "email" | "authority" | "active" | "lastlogin"] case insensitive defaults to userId
     * @param order type of order to sort users [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 0
     * @param offset number of users displayed per page [5 | 10 | 25 | 50] defaults to 10 if invalid
     * @return page of Users dependent on provided page , offset, sort, and order
     */
    public Page<UserDTO> getUserByCreationTime(long epochMilli, int page, int offset, String sortBy, String order){
        page = pageValidation(page);
        sortBy = sortByValidation(sortBy);
        offset = offsetValidation(offset);

        Timestamp timestamp = Timestamp.from(Instant.ofEpochMilli(epochMilli));
        Page<User> users;
        if(order.equalsIgnoreCase("DESC"))
            users = userRepository.findByRegistrationTimeAfter(timestamp, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        else
            users = userRepository.findByRegistrationTimeAfter(timestamp, PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        return users.map(UserDTO.userToDTO());
    }

    /**
     * guarantees a sort field is selected if user provides one, userID as default ofr invalid inputs
     * @param sort field to sort by
     * @return field to sort by, default userID
     */
    private String sortByValidation(String sort){
        switch(sort.toLowerCase(Locale.ROOT)){
            case "name":
                return "name";
            case "email":
                return "email";
            case "registration":
                return "registrationTime";
            case "lastLogin":
                return "lastLogin";
            case "active":
                return "isActive";
            case "authority":
                return "authority";
            default:
                return "userId";
        }
    }

    /**
     * validates page is greater than or equal to 0
     * @param page number of the page available
     * @return number of page to start on
     */
    private int pageValidation(int page){
        if(page < 0)
            page = 0;
        return page;
    }

    /**
     * Validates acceptable offset, resets to 25 if invalid
     * @param offset number of elements desired in each page
     * @return returns 10 as default unless other option selected.
     */
    private int offsetValidation(int offset){
        if(offset != 5 && offset != 10  && offset != 25 && offset != 50){
            offset = 25;
        }
        return offset;
    }


    public void adminCreateUser(User user) {
        userRepository.save(user);
    }
}
