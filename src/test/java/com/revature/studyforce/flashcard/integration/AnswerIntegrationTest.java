package com.revature.studyforce.flashcard.integration;

import com.revature.studyforce.flashcard.controller.AnswerController;
import com.revature.studyforce.flashcard.dto.AnswerDTO;
import com.revature.studyforce.flashcard.model.Answer;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.service.AnswerService;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
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

/**
 * Test class for the AnswerController {@link AnswerController}
 * @author Edson Rodriguez, Kevin Wang
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
class AnswerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private AnswerController answerController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlashcardRepository flashcardRepo;

    @Autowired
    private AnswerService answerService;

    @Test
    void givenFlashcardId_whenGetAllAnswersByFlashcardId_shouldReturnAnswerWithPagination() throws Exception {
        User user = new User(0,"edson@revature.com","Edson Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        User user2 = new User(0,"edson2@revature.com","Edson2 Rodriguez2",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        user = userRepository.save(user);
        user2 = userRepository.save(user2);

        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);
        flashcard = flashcardRepo.save(flashcard);
        AnswerDTO aDTO = new AnswerDTO(1,3,"tcs filename");
        AnswerDTO aDTO2 = new AnswerDTO(2,3,"xml filename");
        answerService.createAnswer(aDTO);
        answerService.createAnswer(aDTO2);

        mockMvc = MockMvcBuilders.standaloneSetup(answerController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/answers/3?page=0&offset=50&sortby=\"creator\"&order=\"DESC\"")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].answerId").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].creator.userId").value(user2.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].flashcard.id").value(flashcard.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].answerText").value("xml filename"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].answerScore").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].selectedAnswer").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].trainerSelected").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].creationTime").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].resolutionTime").isEmpty())

                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].answerId").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].creator.userId").value(user.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].flashcard.id").value(flashcard.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].answerText").value("tcs filename"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].answerScore").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].selectedAnswer").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].trainerSelected").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].creationTime").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].resolutionTime").isEmpty())

                .andReturn();

    }

    @Test
    void givenAnswerId_whenDeleteAnswerById_shouldReturnString() throws Exception {
        User user = new User(0,"edson@revature.com","Edson Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);

        userRepository.save(user);
        flashcardRepo.save(flashcard);

        AnswerDTO aDTO = new AnswerDTO(1,2,"tcs filename");
        answerService.createAnswer(aDTO);

        mockMvc = MockMvcBuilders.standaloneSetup(answerController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"answerId\":\"3\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andReturn();

         result = mockMvc.perform(MockMvcRequestBuilders.get("/answers/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty())
                 .andReturn();

    }

    @Test
    void givenAnswerDTO_whenCreateNewAnswer_ShouldReturnAnswer() throws Exception {
        User user = new User(0,"edson@revature.com","Edson Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);

        user = userRepository.save(user);
        flashcard = flashcardRepo.save(flashcard);

        mockMvc = MockMvcBuilders.standaloneSetup(answerController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":\"1\",\"flashcardId\":\"2\",\"answer\":\"tcs filename\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.answerId").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creator.userId").value(user.getUserId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.flashcard.id").value(flashcard.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.answerText").value("tcs filename"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.answerScore").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.selectedAnswer").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.trainerSelected").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creationTime").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resolutionTime").isEmpty())
                .andReturn();

    }

    @Test
    void givenPut_AnswerId_Should_ReturnUpdatedAnswer_404IfNotFindId() throws Exception {
        User user = new User(0,"edson@revature.com","Edson Rodriguez",true,false,false, Authority.USER, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        Flashcard flashcard = new Flashcard(0,user,null,"how is your day",1,1,Timestamp.valueOf(LocalDateTime.now()),null,false);

        userRepository.save(user);
        flashcardRepo.save(flashcard);

        AnswerDTO aDTO = new AnswerDTO(1,2,"tcs filename");
        Answer answer = answerService.createAnswer(aDTO);

        mockMvc = MockMvcBuilders.standaloneSetup(answerController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/answers/3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.selectedAnswer").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.trainerSelected").value(true))
                .andReturn();

        result = mockMvc.perform(MockMvcRequestBuilders.put("/answers/4")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();


    }
}
