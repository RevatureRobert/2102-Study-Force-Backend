package com.revature.studyforce.flashcard.model;

import com.revature.studyforce.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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
    @ManyToOne
    private User creator;
    @NotNull
    @ManyToOne
    private Flashcard flashcard;
    @NotNull
    @Column(name = "answer_text")
    private String answerText;
    @NotNull
    @PositiveOrZero
    @Column(name = "answer_score")
    private int answerScore;
    @NotNull
    @Column(name = "selected_answer")
    private boolean selectedAnswer;
    @NotNull
    @Column(name = "trainer_selected")
    private boolean trainerSelected;
    @NotNull
    @Column(name = "creation_time")
    private Timestamp creationTime;
    @Column(name = "resolution_time")
    private Timestamp resolutionTime;

}
