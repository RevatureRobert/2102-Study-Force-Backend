package com.revature.StudyForce.flashcard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author Edson Rodriguez
 */
@Entity
@Table(name = "answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    @Id
    @GeneratedValue
    @Column(name = "answer_id")
    private int answerId;
    @NotNull
    @Column(name = "creator_id")
    private int creatorId;
    @NotNull
    @Column(name = "flashcard_id")
    private int flashcardId;
    @NotNull
    @Column(name = "answer")
    private String answerText;
    @Column(name = "answer_score")
    private int answerScore;
    @Column(name = "selected_answer")
    private boolean selectedAnswer;
    @Column(name = "trainer_selected")
    private boolean trainerSelected;
    @NotNull
    @Column(name = "creation_time")
    private Timestamp creationTime;
    @Column(name = "resolution_time")
    private Timestamp resolutionTime;

}
