package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Topic;
import com.revature.studyforce.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Test class for method in FlashcardAllDTO {@link FlashcardAllDTO}
 * @author Luke Mohr
 */
@SpringBootTest
class FlashcardAllDTOTest {

    @Test
    void givenFlashcard_whenConvertToDTO_shouldReturnFlashcardAllDTO(){
        User user = new User();
        user.setUserId(1);
        Topic topic = new Topic();
        topic.setId(1);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Flashcard flashcard = new Flashcard(1, user, topic, "question", 2, 2, now, null, false);
        FlashcardAllDTO flashcardAllDTO =  FlashcardAllDTO.convertToDTO().apply(flashcard);

        Assertions.assertNotNull(flashcardAllDTO);
        Assertions.assertEquals(flashcard.getId(),flashcardAllDTO.getFlashcardId());
        Assertions.assertEquals(flashcard.getCreator().getUserId(),flashcardAllDTO.getCreatorId());
        Assertions.assertEquals(flashcard.getTopic().getTopicName(),flashcardAllDTO.getTopicName());
        Assertions.assertEquals(flashcard.getQuestion(),flashcardAllDTO.getQuestion());
        Assertions.assertEquals(flashcard.getQuestionDifficultyAverage(),flashcardAllDTO.getDifficulty());
        Assertions.assertEquals(flashcard.isResolved(),flashcardAllDTO.isResolved());
    }
}
