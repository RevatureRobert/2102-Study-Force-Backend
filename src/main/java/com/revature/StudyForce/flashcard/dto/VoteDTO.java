package com.revature.StudyForce.flashcard.dto;

import com.revature.StudyForce.flashcard.model.Vote;
import lombok.*;
import org.springframework.util.Assert;

import java.util.function.Function;

@Getter
@Setter
@AllArgsConstructor
public class VoteDTO {

    private int voteId;
    private int voteValue;
    private int answerId;
    private int userId;

    public static Function<Vote,VoteDTO> voteDTOFunction(){
        return (v) -> {
            Assert.notNull(v.getAnswer(),"Answer object is null");
            Assert.notNull(v.getUser(),"User object is null");

            return new VoteDTO(
                    v.getVoteId(),
                    v.getVoteValue(),
                    v.getAnswer().getAnswerId(),
                    v.getUser().getUserId()
            );
        };
    }
}
