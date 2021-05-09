package com.revature.studyforce.flashcard.integration;

import com.revature.studyforce.flashcard.controller.VoteController;
import com.revature.studyforce.flashcard.model.Answer;
import com.revature.studyforce.flashcard.model.Difficulty;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Vote;
import com.revature.studyforce.flashcard.repository.AnswerRepository;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.repository.VoteRepository;
import com.revature.studyforce.flashcard.service.VoteService;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for VoteController {@link VoteController}
 *
 * @author Nick Zimmerman
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
class VoteIntegrationTest {

  @Autowired private VoteRepository voteRepository;
  @Autowired private UserRepository userRepository;
  @Autowired private AnswerRepository answerRepository;
  @Autowired private VoteService voteService;
  @Autowired private FlashcardRepository flashcardRepository;
  @Autowired private VoteController controller;

  @Test
  void givenVoteDTO_whenCreateAnswerVote_shouldReturnVote() throws Exception {
    User u =
        new User(
            0,
            "jesus.christ@revature.com",
            "Jesus Christ",
            true,
            false,
            false,
            Authority.USER,
            Timestamp.valueOf(LocalDateTime.now()),
            Timestamp.valueOf(LocalDateTime.now()));
    userRepository.save(u);

    Flashcard flashcard = new Flashcard(0, u, null, "how is your day", 1, 1, null, null, false);
    flashcardRepository.save(flashcard);

    Answer a =
        new Answer(
            0,
            u,
            flashcard,
            "check stackoverflow",
            5,
            false,
            false,
            Timestamp.valueOf(LocalDateTime.now()),
            Timestamp.valueOf(LocalDateTime.now()));
    answerRepository.save(a);

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    String string = "{\"answerId\" : \"3\"," + "\"userId\" : \"1\"," + "\"value\" : \"1\"" + "}";
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/flashcards/votes/")
                    .content(string)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                MockMvcResultMatchers.content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Test
  void givenVoteWithBadValue_whenCreateAnswerVote_shouldThrowException() throws Exception {
    User u =
        new User(
            0,
            "jesus.christ@revature.com",
            "Jesus Christ",
            true,
            false,
            false,
            Authority.USER,
            Timestamp.valueOf(LocalDateTime.now()),
            Timestamp.valueOf(LocalDateTime.now()));
    userRepository.save(u);

    Flashcard flashcard = new Flashcard(0, u, null, "how is your day", 1, 1, null, null, false);
    flashcardRepository.save(flashcard);

    Answer a =
        new Answer(
            0,
            u,
            flashcard,
            "check stackoverflow",
            5,
            false,
            false,
            Timestamp.valueOf(LocalDateTime.now()),
            Timestamp.valueOf(LocalDateTime.now()));
    answerRepository.save(a);

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    String string = "{\"answerId\" : \"3\"," + "\"userId\" : \"1\"," + "\"value\" : \"33\"" + "}";
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/flashcards/votes/")
                    .content(string)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                MockMvcResultMatchers.status()
                    .is4xxClientError()) // .content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

    Assertions.assertEquals("Invalid vote value exception", result.getResponse().getErrorMessage());
  }

  @Test
  void givenVoteWithBadUserId_whenCreateAnswerVote_shouldThrowException() throws Exception {
    User u =
        new User(
            0,
            "jesus.christ@revature.com",
            "Jesus Christ",
            true,
            false,
            false,
            Authority.USER,
            Timestamp.valueOf(LocalDateTime.now()),
            Timestamp.valueOf(LocalDateTime.now()));
    userRepository.save(u);

    Flashcard flashcard = new Flashcard(0, u, null, "how is your day", 1, 1, null, null, false);
    flashcardRepository.save(flashcard);

    Answer a =
        new Answer(
            0,
            u,
            flashcard,
            "check stackoverflow",
            5,
            false,
            false,
            Timestamp.valueOf(LocalDateTime.now()),
            Timestamp.valueOf(LocalDateTime.now()));
    answerRepository.save(a);

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    String string = "{\"answerId\" : \"3\"," + "\"userId\" : \"20\"," + "\"value\" : \"1\"" + "}";
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/flashcards/votes/")
                    .content(string)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                MockMvcResultMatchers.status()
                    .is4xxClientError()) // .content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

    Assertions.assertEquals("User not found exception", result.getResponse().getErrorMessage());
  }

  @Test
  void givenVoteWithBadAnswerId_whenCreateAnswerVote_shouldThrowException() throws Exception {
    User u =
        new User(
            0,
            "jesus.christ@revature.com",
            "Jesus Christ",
            true,
            false,
            false,
            Authority.USER,
            Timestamp.valueOf(LocalDateTime.now()),
            Timestamp.valueOf(LocalDateTime.now()));
    userRepository.save(u);

    Flashcard flashcard = new Flashcard(0, u, null, "how is your day", 1, 1, null, null, false);
    flashcardRepository.save(flashcard);

    Answer a =
        new Answer(
            0,
            u,
            flashcard,
            "check stackoverflow",
            5,
            false,
            false,
            Timestamp.valueOf(LocalDateTime.now()),
            Timestamp.valueOf(LocalDateTime.now()));
    answerRepository.save(a);

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    String string = "{\"answerId\" : \"33\"," + "\"userId\" : \"1\"," + "\"value\" : \"1\"" + "}";
    MvcResult result =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/flashcards/votes/")
                    .content(string)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(
                MockMvcResultMatchers.status()
                    .is4xxClientError()) // .content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andReturn();

    Assertions.assertEquals("Answer not found exception", result.getResponse().getErrorMessage());
  }

  @Test
  void givenUserAndAnswer_whenGetVote_shouldReturnVote() throws Exception {
      User u =
              new User(
                      0,
                      "jesus.christ@revature.com",
                      "Jesus Christ",
                      true,
                      false,
                      false,
                      Authority.USER,
                      Timestamp.valueOf(LocalDateTime.now()),
                      Timestamp.valueOf(LocalDateTime.now()));
      userRepository.save(u);
      Flashcard flashcard = new Flashcard(0, u, null, "how is your day", 1, 1, null, null, false);
      flashcardRepository.save(flashcard);
      Answer a =
              new Answer(
                      0,
                      u,
                      flashcard,
                      "check stackoverflow",
                      4,
                      false,
                      false,
                      Timestamp.valueOf(LocalDateTime.now()),
                      Timestamp.valueOf(LocalDateTime.now()));
      answerRepository.save(a);
      Vote v = new Vote(0, 1, a, u);
      voteRepository.save(v);
      MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
      MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/votes?answerId=3&userId=1")
              .contentType(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
              .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
              .andExpect(MockMvcResultMatchers.jsonPath("$.answerId").value(3))
              .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
              .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(1))
              .andReturn();

      System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  void givenBadAnswerId_whenGetVote_shouldThrowError() throws Exception {
    User u =
            new User(
                    0,
                    "jesus.christ@revature.com",
                    "Jesus Christ",
                    true,
                    false,
                    false,
                    Authority.USER,
                    Timestamp.valueOf(LocalDateTime.now()),
                    Timestamp.valueOf(LocalDateTime.now()));
    userRepository.save(u);
    Flashcard flashcard = new Flashcard(0, u, null, "how is your day", 1, 1, null, null, false);
    flashcardRepository.save(flashcard);
    Answer a =
            new Answer(
                    0,
                    u,
                    flashcard,
                    "check stackoverflow",
                    4,
                    false,
                    false,
                    Timestamp.valueOf(LocalDateTime.now()),
                    Timestamp.valueOf(LocalDateTime.now()));
    answerRepository.save(a);
    Vote v = new Vote(0, 1, a, u);
    voteRepository.save(v);
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/votes?answerId=33&userId=1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError())
            .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }

  @Test
  void givenBadUserId_whenGetVote_shouldThrowError() throws Exception {
    User u =
            new User(
                    0,
                    "jesus.christ@revature.com",
                    "Jesus Christ",
                    true,
                    false,
                    false,
                    Authority.USER,
                    Timestamp.valueOf(LocalDateTime.now()),
                    Timestamp.valueOf(LocalDateTime.now()));
    userRepository.save(u);
    Flashcard flashcard = new Flashcard(0, u, null, "how is your day", 1, 1, null, null, false);
    flashcardRepository.save(flashcard);
    Answer a =
            new Answer(
                    0,
                    u,
                    flashcard,
                    "check stackoverflow",
                    4,
                    false,
                    false,
                    Timestamp.valueOf(LocalDateTime.now()),
                    Timestamp.valueOf(LocalDateTime.now()));
    answerRepository.save(a);
    Vote v = new Vote(0, 1, a, u);
    voteRepository.save(v);
    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/votes?answerId=3&userId=11")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError())
            .andReturn();

    System.out.println(result.getResponse().getContentAsString());
  }
}
