package com.revature.studyforce.flashcard.model;

import com.revature.studyforce.user.model.User;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Model to store each vote on submitted flashcard answers
 * @author Elizabeth Ye
 */
@Entity
@Table(name = "answer_vote")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue
    private int voteId;

    private int voteValue;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}