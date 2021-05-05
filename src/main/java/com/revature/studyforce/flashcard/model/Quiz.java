package com.revature.studyforce.flashcard.model;


import com.revature.studyforce.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    @ManyToOne(fetch = FetchType.EAGER)
    private User quizUser;
    @NotNull
    @Column(name = "quiz_name")
    private String quizName;
    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Flashcard> flashcards;



}
