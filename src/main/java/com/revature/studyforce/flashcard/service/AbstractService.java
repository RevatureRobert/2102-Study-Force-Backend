package com.revature.studyforce.flashcard.service;

import java.util.Locale;

/**
 * Abstract service with commonly-used methods
 */
public abstract class AbstractService {
    /**
     * Ensures permitted offset format
     * @param offset The offset value being validated
     * @return A valid offset value
     */
    int validateOffset(int offset){
        if(offset != 5 && offset != 10 && offset != 25 && offset != 50 && offset != 100)
            offset = 25;
        return offset;
    }

    /**
     * Ensures permitted page format
     * @param page The page number value being validated
     * @return A valid page number value
     */
    int validatePage(int page){
        if(page < 0)
            page = 0;
        return page;
    }

    /**
     * Ensures permitted sortby format
     * @param sortBy The sortby value being validated
     * @return A valid sortby value
     */
    String validateSortBy(String sortBy){
        switch (sortBy.toLowerCase(Locale.ROOT)) {
            default:
                return "id";
        }
    }
}
