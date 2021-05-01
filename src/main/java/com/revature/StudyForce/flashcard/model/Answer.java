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
    //TODO:
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

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public int getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(int flashcardId) {
        this.flashcardId = flashcardId;
    }

    public String getAnswer() {
        return answerText;
    }

    public void setAnswer(String answer) {
        this.answerText = answer;
    }

    public int getAnswerScore() {
        return answerScore;
    }

    public void setAnswerScore(int answerScore) {
        this.answerScore = answerScore;
    }

    public boolean isSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(boolean selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public boolean isTrainerSelected() {
        return trainerSelected;
    }

    public void setTrainerSelected(boolean trainerSelected) {
        this.trainerSelected = trainerSelected;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Timestamp getResolutionTime() {
        return resolutionTime;
    }

    public void setResolutionTime(Timestamp resolutionTime) {
        this.resolutionTime = resolutionTime;
    }
}