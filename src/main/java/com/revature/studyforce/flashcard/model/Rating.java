package com.revature.studyforce.flashcard.model;

import com.revature.studyforce.user.model.User;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Model used to store the difficulty ratings of a flashcards {@link Flashcard}, {@link User}, {@link Difficulty}
 *@author Edson Rodriguez
 */
@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @GeneratedValue
    private int id;
    @NotNull
    @ManyToOne
    private Flashcard flashcard;
    @NotNull
    @ManyToOne
    private User user;
    @NotNull
    @Column(name = "rating")
    private Difficulty ratingValue;

}
