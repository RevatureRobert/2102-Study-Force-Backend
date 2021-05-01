package com.revature.StudyForce.flashcard.model;

import com.revature.StudyForce.flashcard.dto.FlashcardDTO;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.revature.StudyForce.user.model.User;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.Function;

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

    public Flashcard(User creator, Topic topic, String question, int questionDifficultyTotal, int questionDifficultyAverage, Timestamp createdTime, Timestamp resolutionTime) {
        this.creator = creator;
        this.topic = topic;
        this.question = question;
        this.questionDifficultyTotal = questionDifficultyTotal;
        this.questionDifficultyAverage = questionDifficultyAverage;
        this.createdTime = createdTime;
        this.resolutionTime = resolutionTime;
    }

}
