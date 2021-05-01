package com.revature.StudyForce;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.StudyForce.flashcard.controller.VoteController;
import com.revature.StudyForce.flashcard.model.Answer;
import com.revature.StudyForce.flashcard.model.Vote;
import com.revature.StudyForce.flashcard.repository.VoteRepo;
import com.revature.StudyForce.flashcard.service.VoteService;
import com.revature.StudyForce.user.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
public class VoteControllerTest {

    @Autowired
    private VoteRepo voteRepo;

    @Autowired
    private VoteController controller;

    @Autowired
    private MockMvc mockMvc;

    private Answer answer = new Answer();
    private User user = new User();
    private Vote vote = new Vote(1,1,answer,user);

    ObjectMapper mapping = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testingTest() {
        Assert.assertTrue(2 == 1 + 1);
    }

    @Test
    public void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }

    @Test
    public void postVoteTest() throws Exception {
        voteRepo.save(vote);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/flashcards/vote")
                .content(mapping.writeValueAsString(vote))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.voteId").exists())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        System.out.println(result.getResponse().getStatus());
    }
}
