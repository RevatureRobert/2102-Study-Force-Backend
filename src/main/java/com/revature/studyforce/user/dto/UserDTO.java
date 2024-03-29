package com.revature.studyforce.user.dto;

import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import java.sql.Timestamp;
import java.util.function.Function;

/**
 * UserDTO to transfer User data
 * @author Lok Kan Kung
 * @author Daniel Bernier
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int userId;

    @Email
    private String email;

    private String name;

    private boolean isActive;

    private boolean isSubscribedFlashcard;

    private boolean isSubscribedStacktrace;

    private Authority authority;

    private Timestamp registrationTime;

    private Timestamp lastLogin;

    /**
     * This function converts User object to UserDTO object
     * If user is null, it throws IllegalArgumentException
     * @return a Function which convert User to UserDTO Object
     */
    public static Function<User, UserDTO> userToDTO(){
        return user -> {
            if(user == null){
                throw new IllegalArgumentException("Parameter user cannot be null");
            }
            return new UserDTO (
                    user.getUserId(),
                    user.getEmail(),
                    user.getName(),
                    user.isActive(),
                    user.isSubscribedFlashcard (),
                    user.isSubscribedStacktrace (),
                    user.getAuthority (),
                    user.getRegistrationTime (),
                    user.getLastLogin ()
            );
        };
    }

    /**
     * This function converts UserDTO object back to User object
     * If userDTO is null, it throws IllegalArgumentException
     * @return a Function which convert UserDTO to User Object
     */
    public static Function<UserDTO, User> dtoToUser(){
        return userDTO -> {
            if(userDTO == null){
                throw new IllegalArgumentException("Parameter userDTO cannot be null");
            }
            return new User (
                    userDTO.getUserId(),
                    userDTO.getEmail(),
                    userDTO.getName(),
                    userDTO.isActive(),
                    userDTO.isSubscribedFlashcard(),
                    userDTO.isSubscribedStacktrace(),
                    userDTO.getAuthority(),
                    userDTO.getRegistrationTime(),
                    userDTO.getLastLogin()
            );
        };
    }
}
