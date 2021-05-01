package com.revature.StudyForce.flashcard.model;

/**
 * Enum used to represent the difficulty of a flashcard
 * @author Edson Rodriguez
 */
public enum Difficulty {
    EASY(1),
    MEDIUM(2),
    HARD(3);

    public final int difficultyValue;

    Difficulty(int value){
        this.difficultyValue = value;
    }
}