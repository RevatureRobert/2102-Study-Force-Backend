package com.revature.studyforce.flashcard.dto;

import com.revature.studyforce.flashcard.model.Vote;
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
@ToString
public class VoteDTO {

    /**
     * Fields used in UI layer
     */
    private int answerId;
    private int userId;
    private int value;

    /**
     * function to take a Vote model and if not null return it as a data transfer object
     * @return A Function that can be used to create a new VoteDTO from a vote object {@link Vote}
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
