package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for StacktraceDTO
 *  When given a stacktrace you can
 *  convert it into a stacktraceDTO.
 *
 * @author Joshua Swanson
 */

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")

class StacktraceDTOTest {

    @Test
    void whenStackTraceToDTOThenCorrectDTOReturned () {
        Stacktrace s = new Stacktrace(1,new User(1,"Test@mail.com","Bob",true,true,true, Authority.ROLE_USER,new java.sql.Timestamp(0),new Timestamp(0)),
                "TestTitle","TestBody",new Technology(4,"TestTech"),new Timestamp(0), 0, null);
        StacktraceDTO dto = new StacktraceDTO(1, 1, "Bob", "TestTitle", "TestBody", 4, "TestTech", 1, new Timestamp(0));
        StacktraceDTO result = StacktraceDTO.stacktraceToDTO().apply(s);
        assertEquals(dto.getUserId(),result.getUserId());
        assertEquals(dto.getUserName(),result.getUserName());
        assertEquals(dto.getTitle(),result.getTitle());
        assertEquals(dto.getBody(),result.getBody());
        assertEquals(dto.getTechnologyId(),result.getTechnologyId());
        assertEquals(dto.getTechnologyName(),result.getTechnologyName());
    }
}