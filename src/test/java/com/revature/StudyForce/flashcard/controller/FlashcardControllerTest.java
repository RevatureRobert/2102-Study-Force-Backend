package com.revature.StudyForce.flashcard.controller;

import com.google.gson.Gson;
import com.revature.StudyForce.flashcard.dto.FlashcardDTO;
import com.revature.StudyForce.flashcard.model.Flashcard;
import com.revature.StudyForce.flashcard.model.Topic;
import com.revature.StudyForce.flashcard.repository.FlashcardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FlashcardControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private FlashcardRepository flashcardRepository;

    List<Flashcard> flashcardList = new ArrayList<>();
    Page<Flashcard> flashcardPage;
    FlashcardDTO flashcardDTO;
    Flashcard flashcard;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        flashcardDTO = new FlashcardDTO();
        flashcard = new Flashcard();
        flashcard.setId(1);
        flashcardList.add(flashcard);
        flashcardPage = new PageImpl<>(flashcardList);
    }

    @Test
    public void getAllTest() throws Exception {
        Mockito.doReturn(flashcardPage).when(flashcardRepository).findAll(any(PageRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/all")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content",hasSize(1)));
        verify(flashcardRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void getAllByDifficultyTest() throws Exception {
        Mockito.doReturn(flashcardPage).when(flashcardRepository).findAll(any(PageRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/difficulty")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content",hasSize(1)));
        verify(flashcardRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void getByIdTest() throws Exception {
        Mockito.doReturn(flashcard).when(flashcardRepository).findById(1);
        mockMvc.perform(MockMvcRequestBuilders.get("/flashcards/id")
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(flashcardDTO)));
        verify(flashcardRepository, times(1)).findById(1);
    }

    @Test
    public void saveTest() throws Exception {
        Mockito.doReturn(flashcard).when(flashcardRepository).save(any(Flashcard.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/flashcards")
                .content(new Gson().toJson(flashcard))
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(flashcardRepository, times(1)).save(any(Flashcard.class));
    }

    @Test
    public void updateTest() throws Exception {
        Mockito.doReturn(flashcard).when(flashcardRepository).findById(1);
        Mockito.doReturn(null).when(flashcardRepository).save(flashcard);
        mockMvc.perform(MockMvcRequestBuilders.put("/flashcards")
                .content(new Gson().toJson(flashcard))
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(flashcardRepository, times(1)).findById(1);
        verify(flashcardRepository, times(1)).save(flashcard);
    }

    @Test
    public void testDelete() throws Exception {
        Mockito.doReturn(flashcard).when(flashcardRepository).findById(1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/flashcards")
                .content(new Gson().toJson(flashcard))
                .content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(flashcardRepository, times(1)).findById(1);
        verify(flashcardRepository, times(1)).delete(flashcard);
    }
}
