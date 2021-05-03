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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
    void getAllStacktraces() {
        Mockito.when(stacktraceRepository.findAll()).thenReturn(stacktraceArrayList);
        List<Stacktrace> returnedStacktraceList = stacktraceService.getAllStacktraces().stream().map(StacktraceDTO.DTOToStacktrace()).collect(Collectors.toList());
        for(int i = 0; i < returnedStacktraceList.size(); i++){
            assertEquals(returnedStacktraceList.get(i).getStacktraceId(),stacktraceArrayList.get(i).getStacktraceId());
        }
    }

    @Test
    void whenGetPageStackTraces_callRepository_getsStacktraceDTOPage() {
        Page<Stacktrace> stacktraces = new PageImpl<>(stacktraceArrayList);

        Mockito.when(stacktraceRepository.findAll(org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(stacktraces);

        Page<StacktraceDTO> response = stacktraceService.getPageStacktraces(0, 5, "title", "DESC");
        assertNotNull(response);
        assertEquals(0, response.getContent().get(0).getStacktraceId());
        assertEquals(0, response.getContent().get(0).getUser().getUserId());
        assertEquals("Test@mail.com", response.getContent().get(0).getUser().getEmail());
        assertEquals("", response.getContent().get(0).getUser().getPassword());
        assertEquals("Bob", response.getContent().get(0).getUser().getFirstName());
        assertEquals("Smith", response.getContent().get(0).getUser().getLastName());
        assertTrue(response.getContent().get(0).getUser().isActive());
        assertTrue(response.getContent().get(0).getUser().isSubscribedFlashcard());
        assertTrue(response.getContent().get(0).getUser().isSubscribedStacktrace());
        assertEquals(Authority.USER, response.getContent().get(0).getUser().getAuthority());
        assertEquals(new Timestamp(0), response.getContent().get(0).getUser().getRegistrationTime());
        assertEquals(new Timestamp(0), response.getContent().get(0).getUser().getLastLogin());
        assertEquals("TestTitle", response.getContent().get(0).getTitle());
        assertEquals("TestBody", response.getContent().get(0).getBody());
        assertEquals(0, response.getContent().get(0).getTechnologyId().getTechnologyId());
        assertEquals("TestTech", response.getContent().get(0).getTechnologyId().getTechnologyName());
        assertEquals(new Timestamp(0), response.getContent().get(0).getCreationTime());
        assertEquals(1, response.getTotalPages());
        assertEquals(1, response.getNumberOfElements());
        System.out.println(response.getContent());
    }

    @Test
    void whenGetPageStackTracesByTechnology_callRepository_getsStacktraceDTOPage() {
        Page<Stacktrace> stacktraces = new PageImpl<>(stacktraceArrayList);

        Mockito.when(stacktraceRepository.findByTechnologyId_technologyId(org.mockito.ArgumentMatchers.isA(Integer.class), org.mockito.ArgumentMatchers.isA(Pageable.class))).thenReturn(stacktraces);

        Page<StacktraceDTO> response = stacktraceService.getAllStacktracesByTechnologyId(0, 0,5, "title", "DESC");
        assertNotNull(response);
        assertEquals(0, response.getContent().get(0).getStacktraceId());
        assertEquals(0, response.getContent().get(0).getUser().getUserId());
        assertEquals("Test@mail.com", response.getContent().get(0).getUser().getEmail());
        assertEquals("", response.getContent().get(0).getUser().getPassword());
        assertEquals("Bob", response.getContent().get(0).getUser().getFirstName());
        assertEquals("Smith", response.getContent().get(0).getUser().getLastName());
        assertTrue(response.getContent().get(0).getUser().isActive());
        assertTrue(response.getContent().get(0).getUser().isSubscribedFlashcard());
        assertTrue(response.getContent().get(0).getUser().isSubscribedStacktrace());
        assertEquals(Authority.USER, response.getContent().get(0).getUser().getAuthority());
        assertEquals(new Timestamp(0), response.getContent().get(0).getUser().getRegistrationTime());
        assertEquals(new Timestamp(0), response.getContent().get(0).getUser().getLastLogin());
        assertEquals("TestTitle", response.getContent().get(0).getTitle());
        assertEquals("TestBody", response.getContent().get(0).getBody());
        assertEquals(0, response.getContent().get(0).getTechnologyId().getTechnologyId());
        assertEquals("TestTech", response.getContent().get(0).getTechnologyId().getTechnologyName());
        assertEquals(new Timestamp(0), response.getContent().get(0).getCreationTime());
        assertEquals(1, response.getTotalPages());
        assertEquals(1, response.getNumberOfElements());
        System.out.println(response.getContent());
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
