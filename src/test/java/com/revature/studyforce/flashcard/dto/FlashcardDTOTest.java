package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.flashcard.model.Topic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Test class for method in FlashcardDTO {@link FlashcardDTO}
 * @author Luke Mohr
 */
@SpringBootTest
class FlashcardDTOTest {

    @Test
    void givenFlashcard_whenConvertToDTO_shouldReturnFlashcardDTO() {
        User user = new User();
        user.setUserId(0);
        Topic topic = new Topic();
        topic.setId(0);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Flashcard flashcard = new Flashcard(0, user, topic, "question", 2, 2, now, now, false);
        FlashcardDTO dto = FlashcardDTO.convertToDTO().apply(flashcard);

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(flashcard.getId(),dto.getFlashcardId());
        Assertions.assertEquals(flashcard.getCreator(), dto.getCreator());
        Assertions.assertEquals(flashcard.getTopic(), dto.getTopic());
        Assertions.assertEquals(flashcard.getQuestion(), dto.getQuestion());
        Assertions.assertEquals(flashcard.getQuestionDifficultyTotal(), dto.getQuestionDifficultyTotal());
        Assertions.assertEquals(flashcard.getQuestionDifficultyAverage(), dto.getQuestionDifficultyAverage());
        Assertions.assertEquals(flashcard.getCreatedTime(), dto.getCreatedTime());
        Assertions.assertEquals(flashcard.getResolutionTime(), dto.getResolutionTime());
        Assertions.assertEquals(flashcard.isResolved(),dto.isResolved());
    }
}
