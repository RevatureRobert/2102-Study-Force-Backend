package com.revature.studyforce.stacktrace.dto;

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
class StacktraceUserDTOTest {

    User testUser;
    StacktraceUserDTO testStacktraceUserDTO;

    @BeforeEach
    public void setup(){
        testUser = new User(1, "test.test.com", "TestPassword", "firstname", "lastname", false, true, true, Authority.USER, null, null);
    }

    @Test
    void userToDTO_UserSuccessfullyParsedTest(){
        StacktraceUserDTO stacktraceUserDTO = StacktraceUserDTO.userToDTO().apply(testUser);
        assertEquals(testUser.getUserId(), stacktraceUserDTO.getUserId());
        assertEquals(testUser.getFirstName(), stacktraceUserDTO.getFirstName());
        assertEquals(testUser.getLastName(), stacktraceUserDTO.getLastName());
    }

    @Test
    void userToDTO_NullUserPassedAndCorrectExceptionThrownTest(){
        assertThrows(IllegalArgumentException.class, () -> StacktraceUserDTO.userToDTO().apply(null));
    }
}