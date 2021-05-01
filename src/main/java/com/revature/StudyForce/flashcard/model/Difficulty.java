package com.revature.StudyForce.flashcard.model;

/**
 * Enum used to represent the difficulty of a flashcard in the Rating model {@link Rating}
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

    /**
     * Method used to create an enum from a Integer
     * @param integer the Integer to convert to to a Difficulty enum
     * @return Difficulty enum from Integer
     */
    public static Difficulty fromInteger(int integer){
        switch(integer){
            case 1: return EASY;
            case 2: return MEDIUM;
            case 3: return HARD;
            default: return null;
        }
    }
}