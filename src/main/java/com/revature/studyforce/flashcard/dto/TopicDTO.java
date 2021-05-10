package com.revature.studyforce.flashcard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object used to move information from and to front-end {@link com.revature.studyforce.flashcard.model.Topic}
 * @author Kevin Wang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TopicDTO {
    String topic;
}
