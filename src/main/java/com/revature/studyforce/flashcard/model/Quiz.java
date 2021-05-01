package com.revature.studyforce.flashcard.model;

import com.revature.studyforce.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


import java.util.Set;

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
    @ManyToOne
    private User quizUser;
    @NotNull
    @Column(name = "quiz_name")
    private String quizName;
    @ManyToMany(mappedBy = "id")
    @Column
    private Set<Flashcard> flashcards;



}
