package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.user.dto.UserNameDTO;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for StackTraceUserDTO
 * @author Joshua Swanson
 */
@SpringBootTest
@TestPropertySource(locations = "classpath: test-test-application.properties")
class StacktraceUserDTOTest {

    User testUser;
    UserNameDTO testUserNameDTO;

    @BeforeEach
    public void setup(){
        testUser = new User(1, "TestPassword", "name", false, true, true, Authority.USER, null, null);
    }

    @Test
    void userToDTO_UserSuccessfullyParsedTest(){
        UserNameDTO userNameDTO = new UserNameDTO(testUser.getUserId(),testUser.getName());
        assertEquals(testUser.getUserId(), userNameDTO.getUserId());
        assertEquals(testUser.getName(), userNameDTO.getName());
    }
}