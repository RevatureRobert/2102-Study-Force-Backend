package com.revature.studyforce.stacktrace.dto;

import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.user.dto.UserDTO;
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
        StacktraceDTO dto = new StacktraceDTO(1,new UserDTO(),"TestTitle","TestBody",new Technology(4,"TestTech"),new Timestamp(0));
        Stacktrace s = new Stacktrace(1,new User(3,"Test@mail.com","testname",true,true,true, Authority.USER,new java.sql.Timestamp(0),new Timestamp(0)),
                "TestTitle","TestBody",new Technology(4,"TestTech"),new Timestamp(0),null);
        StacktraceDTO result = StacktraceDTO.stacktraceToDTO().apply(s);
        assertEquals(dto.getCreator().getUserId(),result.getCreator().getUserId());
        assertEquals(dto.getCreator().getName(),result.getCreator().getName());
        assertEquals(dto.getTitle(),result.getTitle());
        assertEquals(dto.getBody(),result.getBody());
        assertEquals(dto.getTechnology().getTechnologyId(),result.getTechnology().getTechnologyId());
        assertEquals(dto.getTechnology().getTechnologyName(),result.getTechnology().getTechnologyName());
    }
}
