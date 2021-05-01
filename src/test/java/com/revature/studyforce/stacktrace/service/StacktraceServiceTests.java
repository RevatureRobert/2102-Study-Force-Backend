package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Tests for {@link StacktraceService}
 * @author John Stone
 */

@SpringBootTest
class StacktraceServiceTests {

    @MockBean
    private StacktraceRepository stacktraceRepository;

    @Autowired
    private StacktraceService stacktraceService;

    @Test
    void whenGetAllStackTraces_callRepository_getsStacktraceDTOPage() {
        List<Stacktrace> stacktraceArrayList = new ArrayList<>();
        stacktraceArrayList.add(new Stacktrace(0,
                new User(0,"Test@mail.com","","Bob","Smith",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0)),
                "TestTitle", "TestBody", new Technology(0, "TestTech"), new Timestamp(0)));
        Page<Stacktrace> stacktraces = new PageImpl<>(stacktraceArrayList);

        Mockito.when(stacktraceRepository.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(stacktraces);

        Page<StacktraceDTO> response = stacktraceService.getPageStacktraces(0, 5, "title", "DESC");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.getContent().get(0).getStacktraceId());
        Assertions.assertEquals(0, response.getContent().get(0).getUser().getUserId());
        Assertions.assertEquals("Test@mail.com", response.getContent().get(0).getUser().getEmail());
        Assertions.assertEquals("", response.getContent().get(0).getUser().getPassword());
        Assertions.assertEquals("Bob", response.getContent().get(0).getUser().getFirstName());
        Assertions.assertEquals("Smith", response.getContent().get(0).getUser().getLastName());
        Assertions.assertEquals(true, response.getContent().get(0).getUser().isActive());
        Assertions.assertEquals(true, response.getContent().get(0).getUser().isSubscribedFlashcard());
        Assertions.assertEquals(true, response.getContent().get(0).getUser().isSubscribedStacktrace());
        Assertions.assertEquals(Authority.USER, response.getContent().get(0).getUser().getAuthority());
        Assertions.assertEquals(new Timestamp(0), response.getContent().get(0).getUser().getRegistrationTime());
        Assertions.assertEquals(new Timestamp(0), response.getContent().get(0).getUser().getLastLogin());
        Assertions.assertEquals("TestTitle", response.getContent().get(0).getTitle());
        Assertions.assertEquals("TestBody", response.getContent().get(0).getBody());
        Assertions.assertEquals(0, response.getContent().get(0).getTechnologyId().getTechnologyId());
        Assertions.assertEquals("TestTech", response.getContent().get(0).getTechnologyId().getTechnologyName());
        Assertions.assertEquals(new Timestamp(0), response.getContent().get(0).getCreationTime());
        Assertions.assertEquals(1, response.getTotalPages());
        Assertions.assertEquals(1, response.getNumberOfElements());
        System.out.println(response.getContent());
    }
    @Test
    void whenGetAllStackTracesByTechnology_callRepository_getsStacktraceDTOPage() {
        List<Stacktrace> stacktraceArrayList = new ArrayList<>();
        stacktraceArrayList.add(new Stacktrace(0,
                new User(0,"Test@mail.com","","Bob","Smith",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0)),
                "TestTitle", "TestBody", new Technology(0, "TestTech"), new Timestamp(0)));
        Page<Stacktrace> stacktraces = new PageImpl<>(stacktraceArrayList);

        Mockito.when(stacktraceRepository.findByTechnologyId_technologyId(org.mockito.ArgumentMatchers.isA(Integer.class), org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(stacktraces);

        Page<StacktraceDTO> response = stacktraceService.getAllStacktracesByTechnologyId(0, 0,5, "title", "DESC");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.getContent().get(0).getStacktraceId());
        Assertions.assertEquals(0, response.getContent().get(0).getUser().getUserId());
        Assertions.assertEquals("Test@mail.com", response.getContent().get(0).getUser().getEmail());
        Assertions.assertEquals("", response.getContent().get(0).getUser().getPassword());
        Assertions.assertEquals("Bob", response.getContent().get(0).getUser().getFirstName());
        Assertions.assertEquals("Smith", response.getContent().get(0).getUser().getLastName());
        Assertions.assertEquals(true, response.getContent().get(0).getUser().isActive());
        Assertions.assertEquals(true, response.getContent().get(0).getUser().isSubscribedFlashcard());
        Assertions.assertEquals(true, response.getContent().get(0).getUser().isSubscribedStacktrace());
        Assertions.assertEquals(Authority.USER, response.getContent().get(0).getUser().getAuthority());
        Assertions.assertEquals(new Timestamp(0), response.getContent().get(0).getUser().getRegistrationTime());
        Assertions.assertEquals(new Timestamp(0), response.getContent().get(0).getUser().getLastLogin());
        Assertions.assertEquals("TestTitle", response.getContent().get(0).getTitle());
        Assertions.assertEquals("TestBody", response.getContent().get(0).getBody());
        Assertions.assertEquals(0, response.getContent().get(0).getTechnologyId().getTechnologyId());
        Assertions.assertEquals("TestTech", response.getContent().get(0).getTechnologyId().getTechnologyName());
        Assertions.assertEquals(new Timestamp(0), response.getContent().get(0).getCreationTime());
        Assertions.assertEquals(1, response.getTotalPages());
        Assertions.assertEquals(1, response.getNumberOfElements());
        System.out.println(response.getContent());
    }
    @Test
    void whenGetStacktraceById_callRepository_getsCorrectStacktrace(){
        Optional<Stacktrace> stacktrace = Optional.of(new Stacktrace(0,
                new User(0,"Test@mail.com","","Bob","Smith",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0)),
                "TestTitle", "TestBody", new Technology(0, "TestTech"), new Timestamp(0)));
        Mockito.when(stacktraceRepository.findById(0)).thenReturn(stacktrace);
        Mockito.when(stacktraceRepository.findById(255)).thenReturn(Optional.empty());

        StacktraceDTO response = stacktraceService.getStacktraceById(0);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(0, response.getStacktraceId());
        Assertions.assertEquals(0, response.getUser().getUserId());
        Assertions.assertEquals("Test@mail.com", response.getUser().getEmail());
        Assertions.assertEquals("", response.getUser().getPassword());
        Assertions.assertEquals("Bob", response.getUser().getFirstName());
        Assertions.assertEquals("Smith", response.getUser().getLastName());
        Assertions.assertEquals(true, response.getUser().isActive());
        Assertions.assertEquals(true, response.getUser().isSubscribedFlashcard());
        Assertions.assertEquals(true, response.getUser().isSubscribedStacktrace());
        Assertions.assertEquals(Authority.USER, response.getUser().getAuthority());
        Assertions.assertEquals(new Timestamp(0), response.getUser().getRegistrationTime());
        Assertions.assertEquals(new Timestamp(0), response.getUser().getLastLogin());
        Assertions.assertEquals("TestTitle", response.getTitle());
        Assertions.assertEquals("TestBody", response.getBody());
        Assertions.assertEquals(0, response.getTechnologyId().getTechnologyId());
        Assertions.assertEquals("TestTech", response.getTechnologyId().getTechnologyName());
        Assertions.assertEquals(new Timestamp(0), response.getCreationTime());

        System.out.println(response);

        response = stacktraceService.getStacktraceById(255);
        Assertions.assertNull(response);
        System.out.println(response);
    }
}
