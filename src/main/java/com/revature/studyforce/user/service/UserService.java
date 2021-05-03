package com.revature.studyforce.user.service;

import com.revature.studyforce.user.dto.*;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Optional;

/**
 * Service for processing {@link com.revature.studyforce.user.model.User}
 * @author Daniel Reyes
 * @author Daniel Bernier
 */
@Service
public class UserService {
    private static final String ID_NOT_FOUND_MESSAGE = "User matching provided userId not found, is your JSON malformed?";
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * GET ALL USERS
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Users in database
     */
    public Page<UserDTO> getAllUsers(int page, int offset, String sortBy, String order){
        Page<User> users;
        users = userRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        return users.map(UserDTO.userToDTO());
    }

    /**
     * @param id belonging to user
     * @return user with that userId
     */
    public UserDTO getUserById(int id){
        Optional<User> user = userRepository.findById(id);
        return user.map(userMap -> UserDTO.userToDTO().apply(userMap)).orElse(null);
    }

    /**
     * @param email belonging to user
     * @return user
     */
    public UserDTO getUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(userMap -> UserDTO.userToDTO().apply(userMap)).orElse(null);
    }

    /**
     * GET ALL USERS Matching a name
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Users in database with a matching name
     */
    public Page<UserDTO> getUserByName(String name, int page, int offset, String sortBy, String order){
        Page<User> users;
        users = userRepository.findByNameIgnoreCase(name, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        return users.map(UserDTO.userToDTO());
    }

    /**
     * GET ALL USERS by Registration Day
     * @param sortBy sort method
     * @param order asc or desc
     * @param page Page displayed
     * @param offset # of object displayed
     * @return All Users in database who registered after a specific date
     */
    public Page<UserDTO> getUserByCreationTime(Timestamp creation, int page, int offset, String sortBy, String order){
        Page<User> users;
        users = userRepository.findByRegistrationTimeAfter(creation, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        return users.map(UserDTO.userToDTO());
    }

    /**
     * Updates the name of a User in {@link UserRepository}
     * @param userNameDTO A data transfer object containing the user's id and their new name
     * @return The data transfer representation of the updated user
     */
    public UserDTO updateUserName(@NotNull UserNameDTO userNameDTO){
        if(userNameDTO.getName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field name cannot be null, is your JSON malformed?");
        }

        Optional<User> userOptional = userRepository.findById(userNameDTO.getUserId());
        User user;

        if(userOptional.isPresent()){
            user = userOptional.get();
            user.setName(userNameDTO.getName());
            return UserDTO.userToDTO().apply(userRepository.save(user));

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ID_NOT_FOUND_MESSAGE);
        }
    }

    /**
     * Updates the authority of a User in {@link UserRepository}
     * @param userAuthorityDTO A data transfer object containing the user's id and their new authority
     * @return The data transfer representation of the updated user
     */
    public UserDTO updateUserAuthority(@NotNull UserAuthorityDTO userAuthorityDTO){
        if(userAuthorityDTO.getAuthority() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field authority cannot be null, is your JSON malformed?");
        }

        Optional<User> userOptional = userRepository.findById(userAuthorityDTO.getUserId());
        User user;

        if(userOptional.isPresent()){
            user = userOptional.get();
            user.setAuthority(userAuthorityDTO.getAuthority());
            return UserDTO.userToDTO().apply(userRepository.save(user));

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ID_NOT_FOUND_MESSAGE);
        }
    }

    /**
     * Updates the active status of a User in {@link UserRepository}
     * @param userIsActiveDTO A data transfer object containing the user's id and their new active status
     * @return The data transfer representation of the updated user
     */
    public UserDTO updateUserIsActive(@NotNull UserIsActiveDTO userIsActiveDTO){
        Optional<User> userOptional = userRepository.findById(userIsActiveDTO.getUserId());
        User user;

        if(userOptional.isPresent()){
            user = userOptional.get();
            user.setActive(userIsActiveDTO.isActive());
            return UserDTO.userToDTO().apply(userRepository.save(user));

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ID_NOT_FOUND_MESSAGE);
        }
    }

    /**
     * Updates the subscription statuses of a User in {@link UserRepository}
     * @param userSubscriptionsDTO A data transfer object containing the user's id and their new subscription statuses
     * @return The data transfer representation of the updated user
     */
    public UserDTO updateUserSubscriptionStatus(UserSubscriptionsDTO userSubscriptionsDTO){
        Optional<User> userOptional = userRepository.findById(userSubscriptionsDTO.getUserId());
        User user;

        if(userOptional.isPresent()){
            user = userOptional.get();
            user.setSubscribedFlashcard(userSubscriptionsDTO.isSubscribedFlashcard());
            user.setSubscribedFlashcard(userSubscriptionsDTO.isSubscribedStacktrace());
            return UserDTO.userToDTO().apply(userRepository.save(user));

        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ID_NOT_FOUND_MESSAGE);
        }
    }
}
