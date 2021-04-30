package com.revature.StudyForce.flashcard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.revature.StudyForce.user.model.User;

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
    private int id;
    @NotNull
    private Flashcard flashcardId;
    @NotNull
    private User user;
    @NotNull
    @Column(name = "rating")
    private Difficulty ratingValue;

}
