package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.dto.FlashcardAllDTO;
import com.revature.studyforce.flashcard.dto.FlashcardDTO;
import com.revature.studyforce.flashcard.dto.NewFlashcardDTO;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.repository.TopicRepository;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.flashcard.model.Topic;
import com.revature.studyforce.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

/**
 * Flashcard service tests {@link FlashcardService}
 * @author Luke Mohr
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@TestPropertySource(locations = "classpath:test-application.properties")
class FlashcardServiceTest {

    @Autowired
    private FlashcardService flashcardService;

    @MockBean
    private FlashcardRepository flashcardRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TopicRepository topicRepository;

    List<Flashcard> flashcardList = new ArrayList<>();
    List<FlashcardAllDTO> flashcardAllDTOList = new ArrayList<>();
    Page<Flashcard> flashcardPage;
    //Page<FlashcardAllDTO> flashcardAllDTOPage;
    FlashcardDTO flashcardDTO;
    Flashcard flashcard;
    Flashcard flashcard2;
    User user;
    Topic topic;
    Timestamp now;
    NewFlashcardDTO newFlashcard;

    @BeforeEach
    public void setUp() {
        flashcardDTO = new FlashcardDTO();
        user = new User();
        user.setUserId(1);
        topic = new Topic();
        topic.setId(1);
        now = Timestamp.valueOf(LocalDateTime.now());
        newFlashcard = new NewFlashcardDTO(1,1,"question",1);
        flashcard = new Flashcard(1, user, topic, "question", 1, 1, now, now, false);
        flashcard2 = new Flashcard(2, user, topic, "question", 2, 2, now, now, false);
        flashcardList.add(flashcard);
        flashcardList.add(flashcard2);
        flashcardAllDTOList.add(FlashcardAllDTO.convertToDTO().apply(flashcard));
        flashcardPage = new PageImpl<>(flashcardList);
        //flashcardAllDTOPage = new PageImpl<>(flashcardAllDTOList);
    }

    @Test
    void givenPaginationArgs_whenGetAll_shouldReturnPageOfFlashcardAllDTOWithPagination() {
        Mockito.when(flashcardRepository.findAll(any(PageRequest.class))).thenReturn(flashcardPage);

        Page<FlashcardAllDTO> pageFlashcardAllDTOs = flashcardService.getAll(0,3,"difficulty","asc");

        FlashcardAllDTO DTO = pageFlashcardAllDTOs.getContent().get(0);
        Assertions.assertNotNull(DTO);
        Assertions.assertEquals(flashcard.getId(), DTO.getFlashcardId());
        Assertions.assertEquals(flashcard.getCreator().getUserId(), DTO.getCreatorId());
        Assertions.assertEquals(flashcard.getQuestionDifficultyAverage(), DTO.getDifficulty());
        Assertions.assertEquals(flashcard.isResolved(), DTO.isResolved());

        DTO = pageFlashcardAllDTOs.getContent().get(1);
        Assertions.assertNotNull(DTO);
        Assertions.assertEquals(flashcard2.getId(), DTO.getFlashcardId());
        Assertions.assertEquals(flashcard2.getCreator().getUserId(), DTO.getCreatorId());
        Assertions.assertEquals(flashcard2.getQuestionDifficultyAverage(), DTO.getDifficulty());
        Assertions.assertEquals(flashcard2.isResolved(), DTO.isResolved());
    }

    @Test
    void givenDifficulty_whenGetAllByDifficulty_shouldReturnPageOfFlashcardAllDTOWithPagination() {
        flashcardList = new ArrayList<>();
        flashcardList.add(flashcard2);

        flashcardPage = new PageImpl<>(flashcardList);


        Mockito.doReturn(flashcardPage).when(flashcardRepository).findAllByQuestionDifficultyTotal(eq(2), any(PageRequest.class));
        Page<FlashcardAllDTO> DTOs = flashcardService.getAllByDifficulty(0,3,"id","desc", 2);
        FlashcardAllDTO DTO = DTOs.getContent().get(0);

        Assertions.assertNotNull(DTO);
        Assertions.assertEquals(flashcard2.getId(), DTO.getFlashcardId());
        Assertions.assertEquals(flashcard2.getCreator().getUserId(), DTO.getCreatorId());
        Assertions.assertEquals(flashcard2.getQuestionDifficultyAverage(), DTO.getDifficulty());
        Assertions.assertEquals(flashcard2.isResolved(), DTO.isResolved());
    }

    @Test
    void givenFlashcardId_whenGetById_shouldReturnFlashcardAllDTO() {
        Mockito.doReturn(Optional.of(flashcard)).when(flashcardRepository).findById(flashcard.getId());
        FlashcardAllDTO DTO = flashcardService.getById(1);
        Assertions.assertNotNull(DTO);
        Assertions.assertEquals(flashcard.getId(), DTO.getFlashcardId());
        Assertions.assertEquals(flashcard.getCreator().getUserId(), DTO.getCreatorId());
        Assertions.assertEquals(flashcard.getQuestionDifficultyAverage(), DTO.getDifficulty());
        Assertions.assertEquals(flashcard.isResolved(), DTO.isResolved());
    }

    @Test
    void givenUserId_whenFindAllByCreatorUserId_shouldReturnFlashcardAllDTOWithMatchingUserId() {
        Mockito.doReturn(Optional.of(user)).when(userRepository).findById(user.getUserId());
        Mockito.doReturn(flashcardList).when(flashcardRepository).findAllByCreator_userId(user.getUserId());
        Page<FlashcardAllDTO> DTO = flashcardService.getByUserId(1);
        FlashcardAllDTO flashcardAllDTO = DTO.getContent().get(0);

        Assertions.assertNotNull(DTO);
        Assertions.assertEquals(flashcard.getId(), flashcardAllDTO.getFlashcardId());
        Assertions.assertEquals(flashcard.getCreator().getUserId(), flashcardAllDTO.getCreatorId());
        Assertions.assertEquals(flashcard.getTopic().getTopicName(),flashcardAllDTO.getTopicName());
        Assertions.assertEquals(flashcard.getQuestion(),flashcardAllDTO.getQuestion());
        Assertions.assertEquals(flashcard.getQuestionDifficultyAverage(), flashcardAllDTO.getDifficulty());
        Assertions.assertEquals(flashcard.isResolved(), flashcardAllDTO.isResolved());
    }


    @Test
    void givenNewFlashcardDTO_whenSave_shouldReturnFlashcardDTO() {
        Mockito.doReturn(flashcard).when(flashcardRepository).save(any(Flashcard.class));
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(topicRepository.findById(1)).thenReturn(Optional.of(topic));

        FlashcardDTO DTO = flashcardService.save(newFlashcard);

        Assertions.assertNotNull(DTO);
        Assertions.assertEquals(flashcard.getId(),DTO.getFlashcardId());
        Assertions.assertEquals(flashcard.getCreator(), DTO.getCreator());
        Assertions.assertEquals(flashcard.getTopic(), DTO.getTopic());
        Assertions.assertEquals(flashcard.getQuestion(),DTO.getQuestion());
        Assertions.assertEquals(flashcard.getQuestionDifficultyTotal(), DTO.getQuestionDifficultyTotal());
        Assertions.assertEquals(flashcard.getQuestionDifficultyAverage(), DTO.getQuestionDifficultyAverage());
        Assertions.assertEquals(flashcard.getCreatedTime(), DTO.getCreatedTime());
        Assertions.assertEquals(flashcard.getResolutionTime(), DTO.getResolutionTime());
        Assertions.assertEquals(flashcard.isResolved(),DTO.isResolved());
    }

    @Test
    void givenFlashcard_whenUpdate_shouldReturnFlashcardDTO() {
        Mockito.when(flashcardRepository.findById(flashcard.getId())).thenReturn(Optional.of(flashcard));
        Mockito.when(flashcardRepository.save(org.mockito.ArgumentMatchers.isA(Flashcard.class))).thenReturn(flashcard);

        FlashcardDTO DTO = flashcardService.update(flashcard);
        Assertions.assertNotNull(DTO);
        Assertions.assertEquals(flashcard.getId(),DTO.getFlashcardId());
        Assertions.assertEquals(flashcard.getCreator(), DTO.getCreator());
        Assertions.assertEquals(flashcard.getTopic(), DTO.getTopic());
        Assertions.assertEquals(flashcard.getQuestion(),DTO.getQuestion());
        Assertions.assertEquals(flashcard.getQuestionDifficultyTotal(), DTO.getQuestionDifficultyTotal());
        Assertions.assertEquals(flashcard.getQuestionDifficultyAverage(), DTO.getQuestionDifficultyAverage());
        Assertions.assertEquals(flashcard.getCreatedTime(), DTO.getCreatedTime());
        Assertions.assertEquals(flashcard.getResolutionTime(), DTO.getResolutionTime());
        Assertions.assertEquals(flashcard.isResolved(),DTO.isResolved());
    }

    @Test
    void givenNull_whenUpdate_shouldThrowException() {
        Mockito.when(flashcardRepository.findById(flashcard.getId())).thenReturn(Optional.of(flashcard));
        Mockito.when(flashcardRepository.save(org.mockito.ArgumentMatchers.isA(Flashcard.class))).thenReturn(flashcard);

        Flashcard f2 = new Flashcard();
        f2.setId(-1);

        Assertions.assertThrows(ResponseStatusException.class, () -> flashcardService.update(f2));
    }

    @Test
    void givenFlashcardId_whenDelete_shouldDeleteFlashcard() {
        flashcardService.delete(1);
        Mockito.verify(flashcardRepository, Mockito.times(1)).deleteById(1);
    }
}
