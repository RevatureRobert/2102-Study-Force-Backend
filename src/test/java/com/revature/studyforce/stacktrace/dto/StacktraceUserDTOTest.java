package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.user.dto.UserDTO;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for StackTraceUserDTO
 * @author Joshua Swanson
 */
@SpringBootTest
class UserDTOTest {

    User testUser;
    UserDTO testStacktraceUserDTO;

    @BeforeEach
    public void setup(){
        testUser = new User(1, "test.test.com", "TestName",  false, true, true, Authority.USER, null, null);
    }

    @Test
    void userToDTO_UserSuccessfullyParsedTest(){
        UserDTO stacktraceUserDTO = UserDTO.userToDTO().apply(testUser);
        assertEquals(testUser.getUserId(), stacktraceUserDTO.getUserId());
        assertEquals(testUser.getName(), stacktraceUserDTO.getName());
    }

    @Test
    void userToDTO_NullUserPassedAndCorrectExceptionThrownTest(){
        assertThrows(IllegalArgumentException.class, () -> UserDTO.userToDTO().apply(null));
    }
}