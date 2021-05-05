package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.Function;

/**
 * DTO used to send as Response within StackTrace and Solution DTOs
 *
 * @author Joshua Swanson
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StacktraceUserDTO {

    private int userId;
    private String firstName;
    private String lastName;

    /**
     * Returns a function used to converts User to StacktraceUserDTO
     * @return Function used to convert User to StacktraceUserDTO
     */
    public static Function<User, StacktraceUserDTO> userToDTO(){
        return user -> {
            if(user == null){
                throw new IllegalArgumentException("Parameter user cannot be null");
            }
            return new StacktraceUserDTO(
                    user.getUserId(),
                    user.getFirstName(),
                    user.getLastName()
            );
        };
    }
}
