package com.revature.StudyForce.flashcard.service;

import com.revature.StudyForce.flashcard.model.Answer;
import com.revature.StudyForce.flashcard.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class AnswerService {
    private AnswerRepository ANSWER_REPO;

    @Autowired
    public AnswerService(AnswerRepository answerRepository){
        this.ANSWER_REPO=answerRepository;
    }

    public Answer createAnswer(Answer newAnswer){
        return ANSWER_REPO.save(newAnswer);
    }

    public void deleteAnswerById(int id){
        ANSWER_REPO.deleteById(id);
    }

    public Page<Answer> getAllByFlashcardById(int flashcardId, int page, int offset, String sortBy, String order){
        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Answer> answers;

        if(order.equalsIgnoreCase("DESC"))
            answers = ANSWER_REPO.findByFlashcardId(flashcardId, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        else
            answers = ANSWER_REPO.findByFlashcardId(flashcardId, PageRequest.of(page, offset, Sort.by(sortBy).ascending()));

        return answers;
    }

    /**
     * Ensures permitted offset format
     * @param offset The offset value being validated
     * @return A valid offset value
     */
    private int validateOffset(int offset){
        if(offset != 5 && offset != 10 && offset != 25 && offset != 50 && offset != 100)
            offset = 25;
        return offset;
    }

    /**
     * Ensures permitted page format
     * @param page The page number value being validated
     * @return A valid page number value
     */
    private int validatePage(int page){
        if(page < 0)
            page = 0;
        return page;
    }

    /**
     * Ensures permitted sortby format
     * @param sortBy The sortby value being validated
     * @return A valid sortby value
     */
    private String validateSortBy(String sortBy){
        switch (sortBy.toLowerCase(Locale.ROOT)) {
            case "answerscore":
                return "answerScore";
            case "creationtime":
                return "creationTime";
            case "resolutiontime":
                return "resolutionTime";
            default:
                return "creatorId";

        }
    }
}
