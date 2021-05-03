package com.revature.studyforce.flashcard.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.revature.studyforce.user.model.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Flashcard model
 * @author Luke
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flashcard {

    @Id
    @GeneratedValue
    private int id;
    @NotNull
    @ManyToOne
    private User creator;
    @ManyToOne
    private Topic topic;
    @Column(nullable = false)
    private String question;
    private int questionDifficultyTotal = 0;
    private int questionDifficultyAverage = 0;
    private Timestamp createdTime = Timestamp.valueOf(LocalDateTime.now());
    private Timestamp resolutionTime = Timestamp.valueOf(LocalDateTime.now());
    private boolean isResolved = false;

}
