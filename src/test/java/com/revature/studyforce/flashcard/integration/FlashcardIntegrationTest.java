package com.revature.studyforce.flashcard.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.studyforce.flashcard.controller.FlashcardController;
import com.revature.studyforce.flashcard.dto.FlashcardDTO;
import com.revature.studyforce.flashcard.dto.NewFlashcardDTO;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Topic;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.repository.TopicRepository;
import com.revature.studyforce.user.model.Authority;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Flashcard integration tests {@link FlashcardController}
 * @author Luke Mohr
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class FlashcardIntegrationTest {

    private MockMvc mockMvc;

    private WebApplicationContext context;

    @Autowired
    private FlashcardController flashcardController;

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TopicRepository topicRepository;

    List<Flashcard> flashcardList = new ArrayList<>();
    Page<Flashcard> flashcardPage;
    FlashcardDTO flashcardDTO;
    NewFlashcardDTO newFlashcardDTO;
    Flashcard flashcard;
    User user;
    Topic topic;

    @BeforeEach
    public void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(flashcardController).build();
        flashcardDTO = new FlashcardDTO();
        user = new User(0,"a@b.c","pw","fn","ln",true,false,false, Authority.USER, null,null);
        user.setLastLogin(null);
        user.setRegistrationTime(null);
        topic = new Topic();
        topic.setId(0);
        topic.setTopicName("java");
        flashcard = new Flashcard(0,user,topic,"question",2,2,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()), false);
        flashcard.setCreatedTime(null);
        flashcard.setResolutionTime(null);
        flashcardList.add(flashcard);
        flashcardPage = new PageImpl<>(flashcardList);
        newFlashcardDTO = new NewFlashcardDTO(1, 2, flashcard.getQuestion(), flashcard.getQuestionDifficultyTotal());

        System.out.println(userRepository.save(user));
        System.out.println(topicRepository.save(topic));
        System.out.println(flashcardRepository.save(flashcard));
        System.out.println(new ObjectMapper().writeValueAsString(newFlashcardDTO));
    }

    @Test
    void whenGetAllWithPagination_shouldReturnPageOfFlashcardAllDTOWithPagination() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/flashcards")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].creatorId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].question").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].difficulty").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].resolved").isBoolean());
    }

    @Test
    void givenDifficultyValue_whenDetAllByDifficulty_shouldReturnPageOfFlashcardAllDTOWithPagination() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/difficulty?difficulty=2")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].creatorId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].question").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].difficulty").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].resolved").isBoolean());
    }

    @Test
    void givenTopicId_whenGetAllByTopic_shouldReturnPageOfFlashcardAllDTOWithPagination() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/topics?topicName=java")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].creatorId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].question").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].difficulty").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].resolved").isBoolean());
    }

    @Test
    void givenResolvedStatus_whenGetAllByIsResolved_shouldReturnPageOfFlashcardAllDTOWithPagination() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/resolved?resolved=false")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].creatorId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].question").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].difficulty").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].resolved").isBoolean());
    }

    @Test
    void givenFlashcardId_whenGetById_shouldReturnFlashcardDTO() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creatorId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.question").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.difficulty").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resolved").isBoolean());

    }

    @Test
    void givenNewFlashcardDTO_whenSave_shouldReturnFlashcardDTO() throws Exception {

//        mockMvc = MockMvcBuilders.standaloneSetup(flashcardController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/flashcards")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"topicId\":2,\"question\":\"question\",\"difficulty\":2}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creator").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.topic.topicName").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.question").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyAverage").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTime").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resolutionTime").doesNotExist());

    }

    @Test
    void givenFlashcard_whenUpdate_shouldReturnFlashcardDTO() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/flashcards")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":3,\"creator\":{\"userId\":1},\"topic\":{\"id\":2},\"question\":\"question\",\"questionDifficultyTotal\":2,\"questionDifficultyAverage\":2,\"createdTime\":null,\"resolutionTime\":null,\"resolved\":false}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creator").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.topic.topicName").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.question").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyAverage").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTime").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resolutionTime").doesNotExist());
    }

    @Test
    void givenFlashcardId_whenDelete_shouldDeleteFlashcard() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/flashcards/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"topicId\":2,\"question\":\"question\",\"difficulty\":2}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));
    }
}
