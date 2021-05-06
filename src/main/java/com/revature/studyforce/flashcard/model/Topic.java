package com.revature.studyforce.flashcard.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Topic model
 * @author Kevin Wang
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Topic {
    @Id
    @GeneratedValue
    int id;

    @NotNull
    @Column(unique = true)
    String topicName;
}
