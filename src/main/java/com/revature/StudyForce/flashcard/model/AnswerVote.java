package com.revature.StudyForce.flashcard.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "answer_vote")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AnswerVote {

    @Id
    @GeneratedValue
    private int voteId;
    private int voteValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
