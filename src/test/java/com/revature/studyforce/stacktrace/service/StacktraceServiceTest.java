package com.revature.studyforce.stacktrace.service;

import com.revature.studyforce.stacktrace.dto.StacktraceDTO;
import com.revature.studyforce.stacktrace.model.Stacktrace;
import com.revature.studyforce.stacktrace.model.Technology;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link StacktraceService}
 * @author John Stone
 * @author Joshua Swanson
 */

@SpringBootTest
class StacktraceServiceTest {

    List<Stacktrace> stacktraceArrayList;

    @MockBean
    private StacktraceRepository stacktraceRepository;

    @Autowired
    private StacktraceService stacktraceService;

    @BeforeEach
    private void beforeEach() {
        stacktraceArrayList = new ArrayList<>();
        stacktraceArrayList.add(
                new Stacktrace(0,
                        new User(0,"Test@mail.com","","Bob","Smith",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0)),
                        "TestTitle",
                        "TestBody",
                        new Technology(0, "TestTech"),
                        new Timestamp(0),
                        null)
        );
    }

    @Test
    void whenGetAllStackTraces_thenAllStackTracesRetrieved() {
        Mockito.when(stacktraceRepository.findAll()).thenReturn(stacktraceArrayList);
        List<Stacktrace> returnedStacktraceList = stacktraceService.getAllStacktraces().stream().map(StacktraceDTO.DTOToStacktrace()).collect(Collectors.toList());
        for(int i = 0; i < returnedStacktraceList.size(); i++){
            assertEquals(returnedStacktraceList.get(i).getStacktraceId(),stacktraceArrayList.get(i).getStacktraceId());
        }
    }

    @Test
    void whenGetAllStackTracesbyTechnologyId_thenStackTracesRetrieved() {
        Mockito.when(stacktraceRepository.findByTechnologyIdTechnologyName("TestTech")).thenReturn(stacktraceArrayList);
        List<Stacktrace> returnedStacktraceList = stacktraceService.getAllStacktraces().stream().map(StacktraceDTO.DTOToStacktrace()).collect(Collectors.toList());
        for(int i = 0; i < returnedStacktraceList.size(); i++){
            assertEquals(returnedStacktraceList.get(i).getStacktraceId(),stacktraceArrayList.get(i).getStacktraceId());
        }
    }

    @Test
    void whenGetStacktraceById_callRepository_getsCorrectStacktrace(){
        Optional<Stacktrace> stacktrace = Optional.of(new Stacktrace(0,
                new User(0,"Test@mail.com","","Bob","Smith",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0)),
                "TestTitle", "TestBody", new Technology(0, "TestTech"), new Timestamp(0), null));
        Mockito.when(stacktraceRepository.findById(0)).thenReturn(stacktrace);
        Mockito.when(stacktraceRepository.findById(255)).thenReturn(Optional.empty());

        StacktraceDTO response = stacktraceService.getStacktraceById(0);
        assertNotNull(response);
        assertEquals(0, response.getStacktraceId());
        assertEquals(0, response.getUser().getUserId());
        assertEquals("Test@mail.com", response.getUser().getEmail());
        assertEquals("", response.getUser().getPassword());
        assertEquals("Bob", response.getUser().getFirstName());
        assertEquals("Smith", response.getUser().getLastName());
        assertTrue(response.getUser().isActive());
        assertTrue(response.getUser().isSubscribedFlashcard());
        assertTrue(response.getUser().isSubscribedStacktrace());
        assertEquals(Authority.USER, response.getUser().getAuthority());
        assertEquals(new Timestamp(0), response.getUser().getRegistrationTime());
        assertEquals(new Timestamp(0), response.getUser().getLastLogin());
        assertEquals("TestTitle", response.getTitle());
        assertEquals("TestBody", response.getBody());
        assertEquals(0, response.getTechnologyId().getTechnologyId());
        assertEquals("TestTech", response.getTechnologyId().getTechnologyName());
        assertEquals(new Timestamp(0), response.getCreationTime());

        System.out.println(response);

        response = stacktraceService.getStacktraceById(255);
        assertNull(response);
        System.out.println(response);
    }

    /**
     * Test for deleteStackTraceById()
     */
    @Test
    void deleteStackTraceByIdTest(){
        Mockito.doNothing().when(stacktraceRepository).deleteById(1);
        stacktraceService.deleteStackTraceById(1);
        verify(stacktraceRepository, times(1)).deleteById(1);
    }
}
