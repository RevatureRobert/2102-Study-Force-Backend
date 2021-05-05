package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StacktraceDTOTest {
    @Test
    void whenStackTraceToDTOThenCorrectDTOReturned () {
        StacktraceDTO dto = new StacktraceDTO(1,new StacktraceUserDTO(3,"Bob","Smith"),"TestTitle","TestBody",new Technology(4,"TestTech"),new Timestamp(0),null);
        Stacktrace s = new Stacktrace(1,new User(3,"Test@mail.com","","Bob","Smith",true,true,true, Authority.USER,new java.sql.Timestamp(0),new Timestamp(0)),
                "TestTitle","TestBody",new Technology(4,"TestTech"),new Timestamp(0),null);
        StacktraceDTO result = StacktraceDTO.stacktraceToDTO().apply(s);
        assertEquals(dto.getUser().getUserId(),result.getUser().getUserId());
        assertEquals(dto.getUser().getFirstName(),result.getUser().getFirstName());
        assertEquals(dto.getUser().getLastName(),result.getUser().getLastName());
        assertEquals(dto.getTitle(),result.getTitle());
        assertEquals(dto.getBody(),result.getBody());
        assertEquals(dto.getTechnologyId().getTechnologyId(),result.getTechnologyId().getTechnologyId());
        assertEquals(dto.getTechnologyId().getTechnologyName(),result.getTechnologyId().getTechnologyName());
    }
}
