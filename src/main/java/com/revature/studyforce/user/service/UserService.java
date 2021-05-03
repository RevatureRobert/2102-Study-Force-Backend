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


    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * GET mapping for '/findALL' in {@link UserRepository#findAll()}
     * @param sortBy field to be sorted by [userId | email | name]  case insensitive defaults to userId
     * @param order type of order to sort users [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 0
     * @param offset number of Users displayed per page [10/ 20/ 30/ 50] defaults to 10 if invalid
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
     * GET request for 'findById' in {@link UserRepository#findById(Object)}
     * @param id int input belonging to user
     * @return single user return that matches userId Param
     */
    public UserDTO getUserById(int id){
        Optional<User> user = userRepository.findById(id);
        return user.map(userMap -> UserDTO.userToDTO().apply(userMap)).orElse(null);
    }

    /**
     * GET request for 'findByEmail' in {@link UserRepository#findByEmail(String)}
     * @param email belonging to user
     * @return single user with matching email
     */
    public UserDTO getUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(userMap -> UserDTO.userToDTO().apply(userMap)).orElse(null);
    }

    /**
     * GET mapping for '/findByNameIgnoreCase' in {@link UserRepository#findByNameContainingIgnoreCase(String, Pageable)}
     * @param name name to compare
     * @param sortBy field to be sorted by [userId | email | name]  case insensitive defaults to userId
     * @param order type of order to sort users [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 0
     * @param offset number of Users displayed per page [10/ 20/ 30/ 50] defaults to 10 if invalid
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
     * GET mapping for '/findByRegistrationTimeAfter' in {@link UserRepository#findByRegistrationTimeAfter(Timestamp, Pageable)}
     * @param epochMilli timestamp to check
     * @param sortBy field to be sorted by [userId | email | name]  case insensitive defaults to userId
     * @param order type of order to sort users [asc | desc] case insensitive - defaults to asc
     * @param page page to be displayed [page >= 0] defaults to 0
     * @param offset number of users displayed per page [10/ 20/ 30/ 50] defaults to 10 if invalid
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
            case "time":
                return "time";
            default:
                return "userId";
        }
    }

    /**
     * guarantees a page to start off if incorrect input provided by user
     * @param page number of the page available
     * @return number of page to start on
     */
    private int pageValidation(int page){
        if(page < 0)
            page = 0;
        return page;
    }

    /**
     * Ensures the number of elements a page can fit, 10 by default
     * @param offset number of elements desired in each page
     * @return returns 10 as default unless other option selected.
     */
    private int offsetValidation(int offset){
        if(offset < 10 || offset > 100){
            offset = 10;
        }
        return offset;
    }




}
