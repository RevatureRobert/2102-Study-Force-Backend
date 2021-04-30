package com.revature.StudyForce.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown if user requested user details cannot be persisted
 *
 * @author Joshua Swanson
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MalformedUserDetailsException extends RuntimeException{

    public MalformedUserDetailsException(String message){
        super(message);
    }
}
