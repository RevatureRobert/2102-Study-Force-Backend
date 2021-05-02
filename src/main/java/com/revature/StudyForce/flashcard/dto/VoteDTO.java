package com.revature.StudyForce.flashcard.dto;
import com.revature.StudyForce.flashcard.model.Vote;

import lombok.*;
import org.springframework.util.Assert;

import java.util.function.Function;

/**
 * Data Transfer Object to carry the Vote model's information to and from the front end
 * {@link Vote}
 * @author Elizabeth Ye
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {

    /**
     * Fields used in UI layer
     */
    private int userId;
    private int value;
    private int answerId;

    /**
     * function to take a Vote model and if not null return it as a data transfer object
     * @return a new VoteDTO with the corresponding fields
     */
    public static Function<Vote,VoteDTO> convertVoteToDto(){
        return v -> {
            Assert.notNull(v.getAnswer(),"Answer object is null");
            Assert.notNull(v.getUser(),"User object is null");

            return new VoteDTO(
                    v.getAnswer().getAnswerId(),
                    v.getUser().getUserId(),
                    v.getVoteValue()
            );
        };
    }
}
