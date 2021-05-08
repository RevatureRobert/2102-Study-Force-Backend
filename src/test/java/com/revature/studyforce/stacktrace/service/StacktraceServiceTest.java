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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Tests for {@link StacktraceService}
 * @author John Stone
 * @author Joshua Swanson
 */

@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StacktraceServiceTest {

    List<Stacktrace> stacktraceArrayList;

    List<StacktraceDTO> stacktraceDTOArrayList;

    @MockBean
    private StacktraceRepository stacktraceRepository;

    @Autowired
    private StacktraceService stacktraceService;

    @BeforeEach
    private void beforeEach() {
        stacktraceArrayList = new ArrayList<>();
        stacktraceArrayList.add(
                new Stacktrace(0,
                        new User(2,"Test@mail.com","Bob",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0)),
                        "TestTitle",
                        "TestBody",
                        new Technology(0, "TestTech"),
                        new Timestamp(0),
                        null)
        );
        stacktraceDTOArrayList = new ArrayList<>();
        stacktraceDTOArrayList.add(
                new StacktraceDTO(1,
                        1,
                        "Bob",
                        "TestTitle",
                        "TestBody",
                        0,
                        "TestTech",
                        new Timestamp(0))
        );
    }

/*    @Test
    void whenGetAllStackTraces_thenAllStackTracesRetrieved() {
        Mockito.when(stacktraceRepository.findAll()).thenReturn(stacktraceArrayList);
        Page<StacktraceDTO> returnedStacktraceList = new PageImpl<>(stacktraceDTOArrayList);
        stacktraceService.getAllStacktraces(0,0);
        for(int i = 0; i < returnedStacktraceList.getContent().size(); i++){
            assertEquals(returnedStacktraceList.getContent().get(i).getStacktraceId(),stacktraceArrayList.get(i).getStacktraceId());
        }
    }

    @Test
    void whenGetAllStackTracesByTitleOrBodyOrTechnologyId_thenStackTracesRetrieved() {
        Mockito.when(stacktraceRepository.findByTechnologyTechnologyName("TestTech")).thenReturn(stacktraceArrayList);
        Page<StacktraceDTO> returnedStacktraceList = stacktraceService.getAllStacktraces(0,0);
        for(int i = 0; i < returnedStacktraceList.getContent().size(); i++){
            assertEquals(returnedStacktraceList.getContent().get(i).getStacktraceId(),stacktraceArrayList.get(i).getStacktraceId());
        }
    }*/

    @Test
    void whenGetStacktraceById_callRepository_getsCorrectStacktrace(){
        Optional<Stacktrace> stacktrace = Optional.of(new Stacktrace(0,
                new User(0,"Test@mail.com","Bob",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0)),
                "TestTitle", "TestBody", new Technology(0, "TestTech"), new Timestamp(0), null));
        Mockito.when(stacktraceRepository.findById(0)).thenReturn(stacktrace);
        Mockito.when(stacktraceRepository.findById(255)).thenReturn(Optional.empty());

        StacktraceDTO response = stacktraceService.getStackTraceById(0);
        assertNotNull(response);
        assertEquals(0, response.getStacktraceId());
        assertEquals("Bob", response.getUserName());
        assertEquals("TestTitle", response.getTitle());
        assertEquals("TestBody", response.getBody());
        assertEquals(0, response.getTechnologyId());
        assertEquals("TestTech", response.getTechnologyName());
        assertEquals(new Timestamp(0), response.getCreationTime());

        System.out.println(response);

        response = stacktraceService.getStackTraceById(255);
        assertNull(response);
    }

    /**
     * Test for deleteStackTraceById()
     */
    @Test
    void whenDeleteStacktraceCalled_ThenDeleteAndReturnStacktrace(){
        Mockito.doReturn(Optional.of(stacktraceArrayList.get(0))).when(stacktraceRepository).findById(1);
        Mockito.doNothing().when(stacktraceRepository).deleteById(1);
        stacktraceService.deleteStackTraceById(1);
        verify(stacktraceRepository, times(1)).deleteById(1);
    }

    @Test
    void whenDeleteStacktraceCalledWithNullParameter_ThenReturnNull(){
        Mockito.doReturn(Optional.ofNullable(null)).when(stacktraceRepository).findById(1);
        assertNull(stacktraceService.deleteStackTraceById(1));
    }

}
