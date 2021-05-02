package com.revature.StudyForce.flashcard.dto;

import com.revature.StudyForce.flashcard.model.Vote;
import lombok.*;
import org.springframework.util.Assert;

import java.util.function.Function;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {

    private int userId;
    private int value;
    private int answerId;

    public static Function<Vote,VoteDTO> functionVoteToDto(){
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
