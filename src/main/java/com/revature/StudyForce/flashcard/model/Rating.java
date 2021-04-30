package com.revature.StudyForce.flashcard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Model used to store the difficulty ratings of a flashcards
 *
 *@author Edson Rodriguez
 */
@Entity
@Table(name = "timesheets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue
    @Column(name = "rating_id")
    private int ratingId;
    @NotNull
    @Column(name = "flashcard_id")
    private int flashcardId;
    @NotNull
    @Column(name = "user_id")
    private int userId;
    @NotNull
    @Column(name = "rating")
    private Difficulty ratingValue;


    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    public int getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(int flashcardId) {
        this.flashcardId = flashcardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Difficulty getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(Difficulty ratingValue) {
        this.ratingValue = ratingValue;
    }
}
