package com.revature.studyforce.flashcard.integration;

import com.google.gson.Gson;
import com.revature.studyforce.flashcard.controller.FlashcardController;
import com.revature.studyforce.flashcard.dto.FlashcardDTO;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Topic;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class FlashcardIntegrationTest {

    private MockMvc mockMvc;

//    @Autowired
//    private WebApplicationContext context;

    @Autowired
    private FlashcardController flashcardController;

    @Autowired
    private FlashcardRepository flashcardRepository;

    @Autowired
    private UserRepository userRepository;

    List<Flashcard> flashcardList = new ArrayList<>();
    Page<Flashcard> flashcardPage;
    FlashcardDTO flashcardDTO;
    Flashcard flashcard;
    User user;

    @BeforeEach
    public void setUp() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        mockMvc = MockMvcBuilders.standaloneSetup(flashcardController).build();
        flashcardDTO = new FlashcardDTO();
        user = new User(0,"a@b.c","pw","fn","ln",true,false,false, Authority.USER, null,null);
        user.setLastLogin(null);
        user.setRegistrationTime(null);
        flashcard = new Flashcard(0,user,null,"question",2,2,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        flashcard.setCreatedTime(null);
        flashcard.setResolutionTime(null);
        flashcardList.add(flashcard);
        flashcardPage = new PageImpl<>(flashcardList);

        System.out.println(userRepository.save(user));
        System.out.println(flashcardRepository.save(flashcard));
        System.out.println(new Gson().toJson(flashcard));
    }

    @Test
    void getAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/all")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].creator").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].question").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].questionDifficultyAverage").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].createdTime").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].resolutionTime").doesNotExist());
    }

    @Test
    void getAllByDifficultyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/difficulty?difficulty=2")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].creator").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].question").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].questionDifficultyAverage").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].createdTime").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].resolutionTime").doesNotExist());
    }

    @Test
    void getByIdTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/id/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creator").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.question").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyAverage").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTime").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resolutionTime").doesNotExist());

    }

    @Test
    void saveTest() throws Exception {
        user.setLastLogin(null);
        user.setRegistrationTime(null);
        flashcard.setCreatedTime(null);
        System.out.println(new Gson().toJson(flashcard));

//        mockMvc = MockMvcBuilders.standaloneSetup(flashcardController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/flashcards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(flashcard)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creator").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.question").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyAverage").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTime").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resolutionTime").isNumber());

    }

    @Test
    void updateTest() throws Exception {
        user.setLastLogin(null);
        user.setRegistrationTime(null);
        flashcard.setCreatedTime(null);
        System.out.println(new Gson().toJson(flashcard));

//        mockMvc = MockMvcBuilders.standaloneSetup(flashcardController).build();
        mockMvc.perform(MockMvcRequestBuilders.put("/flashcards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(flashcard)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creator").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.question").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyAverage").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTime").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resolutionTime").isNumber());
    }

    @Test
    void testDelete() throws Exception {
        user.setLastLogin(null);
        user.setRegistrationTime(null);
        flashcard.setCreatedTime(null);
        System.out.println(new Gson().toJson(flashcard));

//        mockMvc = MockMvcBuilders.standaloneSetup(flashcardController).build();
        mockMvc.perform(MockMvcRequestBuilders.delete("/flashcards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(flashcard)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.creator").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.question").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyTotal").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.questionDifficultyAverage").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdTime").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resolutionTime").isNumber());
    }
}
