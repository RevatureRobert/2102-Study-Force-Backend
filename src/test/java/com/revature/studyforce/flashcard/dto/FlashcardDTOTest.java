package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.flashcard.model.Topic;
import com.revature.studyforce.flashcard.dto.FlashcardDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootTest
class FlashcardDTOTest {

    @Test
    void ConvertToDTOTest() {
        User user = new User();
        user.setUserId(1);
        Topic topic = new Topic();
        topic.setId(1);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Flashcard flashcard = new Flashcard(1, user, topic, "question", 2, 2, now, now);
        FlashcardDTO dto = FlashcardDTO.convertToDTO().apply(flashcard);

        Assertions.assertEquals(user, dto.getCreator());
        Assertions.assertEquals(topic, dto.getTopic());
        Assertions.assertEquals("question", dto.getQuestion());
        Assertions.assertEquals(2, dto.getQuestionDifficultyTotal());
        Assertions.assertEquals(2, dto.getQuestionDifficultyAverage());
        Assertions.assertEquals(now, dto.getCreatedTime());
        Assertions.assertEquals(now, dto.getResolutionTime());
    }

    @Test
    void ConvertFromDTOTest() {
        User user = new User();
        user.setUserId(1);
        Topic topic = new Topic();
        topic.setId(1);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        FlashcardDTO flashcardDTO = new FlashcardDTO(user, topic, "question", 2, 2, now, now);
        Flashcard flashcard = FlashcardDTO.convertFromDTO().apply(flashcardDTO);

        Assertions.assertEquals(user, flashcard.getCreator());
        Assertions.assertEquals(topic, flashcard.getTopic());
        Assertions.assertEquals("question", flashcard.getQuestion());
        Assertions.assertEquals(2, flashcard.getQuestionDifficultyTotal());
        Assertions.assertEquals(2, flashcard.getQuestionDifficultyAverage());
        Assertions.assertEquals(now, flashcard.getCreatedTime());
        Assertions.assertEquals(now, flashcard.getResolutionTime());
    }
}
