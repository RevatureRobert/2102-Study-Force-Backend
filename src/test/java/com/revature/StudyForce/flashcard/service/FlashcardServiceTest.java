package com.revature.StudyForce.flashcard.service;

import com.revature.StudyForce.flashcard.dto.FlashcardDTO;
import com.revature.StudyForce.flashcard.model.Flashcard;
import com.revature.StudyForce.flashcard.repository.FlashcardRepository;
import com.revature.StudyForce.user.model.User;
import com.revature.StudyForce.user.model.Topic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

class FlashcardServiceTest {

    @Autowired
    private FlashcardService flashcardService;

    @MockBean
    private FlashcardRepository flashcardRepository;

    List<Flashcard> flashcardList = new ArrayList<>();
    Page<Flashcard> flashcardPage;
    FlashcardDTO flashcardDTO;
    Flashcard flashcard;
    User user;
    Topic topic;
    Timestamp now;

    @BeforeEach
    public void setUp() {
        flashcardDTO = new FlashcardDTO();
        user = new User();
        user.setUserId(1);
        topic = new Topic();
        topic.setTopicId(1);
        now = Timestamp.valueOf(LocalDateTime.now());
        flashcard = new Flashcard(1, user, topic, "question", 2, 2, now, now);
        flashcardList.add(flashcard);
        flashcardPage = new PageImpl<>(flashcardList);
    }

    @Test
    void getAllFlashcardsTest() {
        Mockito.doReturn(flashcardPage).when(flashcardRepository).findAll(any(PageRequest.class));
        Page<FlashcardDTO> DTOs = flashcardService.getAll(0,3,"id","desc");
        FlashcardDTO DTO = DTOs.getContent().get(0);
        Assertions.assertNotNull(DTO);
        Assertions.assertEquals(1, DTO.getCreator().getUserId());
        Assertions.assertEquals(1, DTO.getTopic().getTopicId());
        Assertions.assertEquals(2, DTO.getQuestionDifficultyTotal());
        Assertions.assertEquals(2, DTO.getQuestionDifficultyAverage());
        Assertions.assertEquals(now, DTO.getCreatedTime());
        Assertions.assertEquals(now, DTO.getResolutionTime());
    }

    @Test
    void getAllByDifficultyTest() {
        Mockito.doReturn(flashcardPage).when(flashcardRepository).findALlByDifficulty(2, any(PageRequest.class));
        Page<FlashcardDTO> DTOs = flashcardService.getAllByDifficulty(0,3,"id","desc", 2);
        FlashcardDTO DTO = DTOs.getContent().get(0);
        Assertions.assertNotNull(DTO);
        Assertions.assertEquals(1, DTO.getCreator().getUserId());
        Assertions.assertEquals(1, DTO.getTopic().getTopicId());
        Assertions.assertEquals(2, DTO.getQuestionDifficultyTotal());
        Assertions.assertEquals(2, DTO.getQuestionDifficultyAverage());
        Assertions.assertEquals(now, DTO.getCreatedTime());
        Assertions.assertEquals(now, DTO.getResolutionTime());
    }

    @Test
    void getByIdTest() {
        Mockito.doReturn(flashcard).when(flashcardRepository).findById(1);
        FlashcardDTO DTO = flashcardService.getById(1);
        Assertions.assertNotNull(DTO);
        Assertions.assertEquals(1, DTO.getCreator().getUserId());
        Assertions.assertEquals(1, DTO.getTopic().getTopicId());
        Assertions.assertEquals(2, DTO.getQuestionDifficultyTotal());
        Assertions.assertEquals(2, DTO.getQuestionDifficultyAverage());
        Assertions.assertEquals(now, DTO.getCreatedTime());
        Assertions.assertEquals(now, DTO.getResolutionTime());
    }

    @Test
    void saveTest() {
        Mockito.doReturn(flashcard).when(flashcardRepository).save(any(Flashcard.class));
        FlashcardDTO DTO = flashcardService.save(flashcard);
        Assertions.assertNotNull(DTO);
        Assertions.assertEquals(1, DTO.getCreator().getUserId());
        Assertions.assertEquals(1, DTO.getTopic().getTopicId());
        Assertions.assertEquals(2, DTO.getQuestionDifficultyTotal());
        Assertions.assertEquals(2, DTO.getQuestionDifficultyAverage());
        Assertions.assertEquals(now, DTO.getCreatedTime());
        Assertions.assertEquals(now, DTO.getResolutionTime());
    }

    @Test
    void updateTest() {
        Mockito.doReturn(flashcard).when(flashcardRepository).findById(1);
        Mockito.doReturn(null).when(flashcardRepository).save(flashcard);
        FlashcardDTO DTO = flashcardService.update(flashcard);
        Assertions.assertNotNull(DTO);
        Assertions.assertEquals(1, DTO.getCreator().getUserId());
        Assertions.assertEquals(1, DTO.getTopic().getTopicId());
        Assertions.assertEquals(2, DTO.getQuestionDifficultyTotal());
        Assertions.assertEquals(2, DTO.getQuestionDifficultyAverage());
        Assertions.assertEquals(now, DTO.getCreatedTime());
        Assertions.assertEquals(now, DTO.getResolutionTime());
    }

    @Test
    void deleteTest() {
        Mockito.doReturn(flashcard).when(flashcardRepository).findById(1);
        FlashcardDTO DTO = flashcardService.delete(flashcard);
        Assertions.assertNotNull(DTO);
        Assertions.assertEquals(1, DTO.getCreator().getUserId());
        Assertions.assertEquals(1, DTO.getTopic().getTopicId());
        Assertions.assertEquals(2, DTO.getQuestionDifficultyTotal());
        Assertions.assertEquals(2, DTO.getQuestionDifficultyAverage());
        Assertions.assertEquals(now, DTO.getCreatedTime());
        Assertions.assertEquals(now, DTO.getResolutionTime());
    }
}
