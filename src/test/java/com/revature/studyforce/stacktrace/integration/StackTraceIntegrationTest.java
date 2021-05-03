package com.revature.studyforce.stacktrace.integration;

import com.revature.studyforce.stacktrace.controller.StacktraceController;
import com.revature.studyforce.stacktrace.repository.StacktraceRepository;
import com.revature.studyforce.stacktrace.service.StacktraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
public class StackTraceIntegrationTest {
    //TODO: Standardize Tests across project
   private MockMvc mockMvc;

    @Autowired
    private StacktraceRepository stacktraceRepository;

    @Autowired
    private StacktraceController stacktraceController;

    @Autowired
    private StacktraceService stacktraceService;

/*    @Test
    public void givenStacktrace_whenGetAll_thenStacktraceRetrieved() throws Exception {
        stacktraceRepository.save(new Stacktrace(1,
                new User(1,"Test@mail.com","Pass","Bob","Smith",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0)),
                "TestTitle", "TestBody", new Technology(1, "TestTech"), new Timestamp(0)));
        mockMvc = MockMvcBuilders.standaloneSetup(stacktraceController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/stacktrace/page")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.email").value("Test@mail.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.password").value("Pass"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.firstName").value("Bob"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.lastName").value("Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.isActive").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.isSubscribedFlashcard").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.isSubscribedStacktrace").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.Authority").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.registrationTime").value(new Time(0).toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].User.lastLogin").value(new Time(0).toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].tile").value("TestTitle"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].body").value("TestBody"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].Technology.technologyId").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].Technology.technologyName").value("TestTech"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].creationTime").value(new Time(0).toString()))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }*/
/*    @Test
    public void givenStacktrace_whenGetAllWithPagination_thenStacktraceRetrieved() throws Exception {
        StacktraceDTO s = new StacktraceDTO(1,
                new User(1,"Test@mail.com","Pass","Bob","Smith",true,true,true, Authority.USER,new Timestamp(0),new Timestamp(0)),
                "TestTitle", "TestBody", new Technology(1, "TestTech"), new Timestamp(0));
        ArrayList<StacktraceDTO> testStacktraceDTOList = new ArrayList<>();
        testStacktraceDTOList.add(s);
        Page<StacktraceDTO> p = new PageImpl<>(testStacktraceDTOList);
        Mockito.doReturn(p).when(stacktraceService).getPageStacktraces(1,0,"stackTraceID","ASC");
        mockMvc.perform(MockMvcRequestBuilders.get("/stacktrace/page", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(stacktraceService, times(1)).getPageStacktraces(1,0,"stackTraceID","ASC");
    }*/
}
