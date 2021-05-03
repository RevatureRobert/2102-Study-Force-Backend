package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.dto.AnswerDTO;
import com.revature.studyforce.flashcard.model.Answer;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.repository.AnswerRepository;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

/**
 * Services for the Answer repository {@link AnswerRepository}
 * @author Edson Rodriguez
 */
@Service
public class AnswerService {
    private AnswerRepository ANSWER_REPO;
    private UserRepository USER_REPO;
    private FlashcardRepository FLASHCARD_REPO;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, FlashcardRepository flashcardRepo, UserRepository userRepository){
        this.ANSWER_REPO=answerRepository;
        this.USER_REPO=userRepository;
        this.FLASHCARD_REPO=flashcardRepo;
    }

    /**
     * Method used to create a new Answer
     * @param answerDTO The dta transfer object with the information required to create a new answer {@link AnswerDTO}
     * @return The newly created answer object
     */
    public Answer createAnswer(AnswerDTO answerDTO){
        Optional<Flashcard> optFlashcard = FLASHCARD_REPO.findById(answerDTO.getFlashcardId());
        Optional<User> optUser = USER_REPO.findById(answerDTO.getUserId());

        if(!optFlashcard.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Flashcard not found exception");
        if(!optUser.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found exception");
        if(answerDTO.getAnswer().equals(""))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Answer is a empty string");


        return ANSWER_REPO.save(new Answer(
                0,
                optUser.get(),
                optFlashcard.get(),
                answerDTO.getAnswer(),
                0,
                false,
                false,
                Timestamp.valueOf(LocalDateTime.now()),
                null
        ));
    }

    /**
     * Deletes an answer by its answerId
     * @param answerId the id of te answer to delete
     */
    public void deleteAnswerById(int answerId){
        ANSWER_REPO.deleteById(answerId);
    }

    /**
     * Method used to retrieve a collection of answers with pagination
     * @param flashcardId The id of the flashcard from which the answers are being fetched
     * @param page The page to be selected defaults to 0 if page could not be understood
     * @param offset The number of elements per page [5|10|25|50|100] defaults to 25 if offset could not be understood
     * @param sortBy The property/field to sort by ["answerScore"|"creationTime"|"resolutionTime"|"creator"] defaults to creator if sortby could not be understood
     * @param order The order in which the list is displayed ["ASC"|"DESC"]
     * @return a Page of answers that match the Flashcard id with pagination and sorting applied
     */
    public Page<Answer> getAllByFlashcardId(int flashcardId, int page, int offset, String sortBy, String order){
        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Answer> answers;

        if(order.equalsIgnoreCase("DESC"))
            answers = ANSWER_REPO.findByFlashcard_id(flashcardId, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        else
            answers = ANSWER_REPO.findByFlashcard_id(flashcardId, PageRequest.of(page, offset, Sort.by(sortBy).ascending()));

        answers.forEach((answer -> {
            answer.getCreator().setPassword("");
            answer.getFlashcard().getCreator().setPassword("");
        }));

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
                return "creator";
        }
    }
}
