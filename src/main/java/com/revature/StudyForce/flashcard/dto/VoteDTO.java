package com.revature.StudyForce.flashcard.dto;

import lombok.*;

@Getter
@Setter
public class VoteDTO {

    private int voteId;
    private int voteValue;
    private int answerId;
    private int userId;
}
