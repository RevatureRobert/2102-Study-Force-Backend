package com.revature.StudyForce.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown if the UserRepository cannot find user by requested id
 *
 * @author Joshua Swanson
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoUserFoundByIdException extends RuntimeException{

    public NoUserFoundByIdException(int id){
        super("No user found by id: " + id);
    }
}
