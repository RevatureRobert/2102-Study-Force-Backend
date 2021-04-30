package com.revature.StudyForce.user.dto;

import com.revature.StudyForce.user.model.Authority;
import com.revature.StudyForce.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.function.Function;

/**
 * UserDTO to transfer User data
 * @author Lok Kan Kung
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private int userId;

    @Email
    private String email;

    @NotNull
    private String password;

    private String firstName;

    private String lastName;

    private boolean isActive;

    private boolean isSubscribedFlashcard;

    private boolean isSubscribedStacktrace;

    private Authority authority;

    private Timestamp registrationTime;

    private Timestamp lastLogin;

    public static Function<User, UserDTO> userToDTO(){
        return user -> {
            if(user == null){
                throw new IllegalArgumentException("Parameter user cannot be null");
            }
            return new UserDTO (
                    user.getUserId (),
                    user.getEmail (),
                    "",
                    user.getFirstName (),
                    user.getLastName (),
                    user.isActive (),
                    user.isSubscribedFlashcard (),
                    user.isSubscribedStacktrace (),
                    user.getAuthority (),
                    user.getRegistrationTime (),
                    user.getLastLogin ()
            );
        };
    }

    public static Function<UserDTO, User> dtoToUser(){
        return userDTO -> {
            if(userDTO == null){
                throw new IllegalArgumentException("Parameter userDTO cannot be null");
            }
            return new User (
                    userDTO.getUserId (),
                    userDTO.getEmail (),
                    userDTO.getPassword (),
                    userDTO.getFirstName (),
                    userDTO.getLastName (),
                    userDTO.isActive (),
                    userDTO.isSubscribedFlashcard (),
                    userDTO.isSubscribedStacktrace (),
                    userDTO.getAuthority (),
                    userDTO.getRegistrationTime (),
                    userDTO.getLastLogin ()
            );
        };
    }
}