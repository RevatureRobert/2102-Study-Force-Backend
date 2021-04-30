package com.revature.StudyForce.flashcard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Quiz model
 *
 * @author Edson Rodriguez
 */
@Entity
@Table(name = "quizzes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue
    @Column(name = "quiz_id")
    private int quizId;
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @NotNull
    @Column(name = "quiz_name")
    private String quizName;
    //TODO:here goes the flashcard model
    //TODO:generate getters and setters for the flashcard property
    //TODO: create the relationship with other models



    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }
}
