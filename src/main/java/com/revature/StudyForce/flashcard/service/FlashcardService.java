package com.revature.StudyForce.flashcard.service;

import com.revature.StudyForce.flashcard.dto.FlashcardDTO;
import com.revature.StudyForce.flashcard.model.Flashcard;
import com.revature.StudyForce.flashcard.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;
/**
 * Services for the Flashcard repository
 * @author Luke
 */
@Service
public class FlashcardService extends AbstractService {

    private final FlashcardRepository FLASHCARD_REPO;

    @Autowired
    public FlashcardService(FlashcardRepository flashcardRepository){
        this.FLASHCARD_REPO=flashcardRepository;
    }

    /**
     * getAll() method mapped to HTTP GET requests ("/all")
     * @param page - number of offsets away from 0
     * @param offset - number of Flashcards per page
     * @param sortBy - column to sort by
     * @param order - ascending or descending order
     * @return - returns Page of paginated Flashcards
     */
    public Page<FlashcardDTO> getAll(int page, int offset, String sortBy, String order){
        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Flashcard> flashcards;

    if (order.equalsIgnoreCase("DESC")) {
      flashcards = FLASHCARD_REPO.findAll(PageRequest.of(page, offset, Sort.by(sortBy).descending()));
    } else {
      flashcards = FLASHCARD_REPO.findAll(PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
    }

        return flashcards.map(FlashcardDTO.convertToDTO());
    }

    /**
     * getAllByDifficulty() method mapped to HTTP GET requests ("/difficulty")
     * @param page - number of offsets away from 0
     * @param offset - number of Flashcards per offset
     * @param sortBy - column to sort by
     * @param order - ascending or descending order
     * @param difficulty - limits returned Flashcards to the given difficulty
     * @return - returns a List of paginated Flashcards sorted by difficulty
     */
    public Page<FlashcardDTO> getAllByDifficulty(int page, int offset, String sortBy, String order, int difficulty) {
        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Flashcard> flashcards;

        if (order.equalsIgnoreCase("DESC")) {
            flashcards = FLASHCARD_REPO.findALlByQuestionDifficultyTotal(difficulty, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        } else {
            flashcards = FLASHCARD_REPO.findALlByQuestionDifficultyTotal(difficulty, PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        }

        return flashcards.map(FlashcardDTO.convertToDTO());
    }

    /**
     * getById() method mapped to HTTP GET requests ("/id/{id}")
     * @param id - limits returned Flashcard to the given id
     * @return - returns Flashcard with the given id
     */
    public FlashcardDTO getById(int id) {
        return FlashcardDTO.convertToDTO().apply(FLASHCARD_REPO.findById(id).orElse(null));
    }

    /**
     * save() method mapped to HTTP POST requests
     * @param flashcard - Flashcard object to persist
     * @return - returns persisted Flashcard
     */
    public FlashcardDTO save(Flashcard flashcard) {
        return FlashcardDTO.convertToDTO().apply(FLASHCARD_REPO.save(flashcard));
    }

    /**
     * update() method mapped to HTTP PUT requests
     * @param flashcard - new Flashcard to replace original in database
     * @return - returns updated Flashcard
     */
    public FlashcardDTO update(Flashcard flashcard) {
        Flashcard original = FLASHCARD_REPO.findById(flashcard.getId()).orElse(null);

        if (original != null) {
            int id = original.getId();
        }
        original = flashcard;
        original.setId(id);

        return FlashcardDTO.convertToDTO().apply(FLASHCARD_REPO.save(original));
    }

    /**
     * delete() method mapped to HTTP DELETE requests
     * @param flashcard - Flashcard to be deleted from the database
     * @return - returns deleted Flashcard
     */
    public @ResponseBody
    FlashcardDTO delete(@RequestBody Flashcard flashcard) {
        FLASHCARD_REPO.delete(flashcard);
        return FlashcardDTO.convertToDTO().apply(flashcard);
    }

    @Override
    String validateSortBy(String sortBy){
        switch (sortBy.toLowerCase(Locale.ROOT)) {
            case "difficulty":
                return "questionDifficultyTotal";
            case "topic":
                return "topic";
            case "created":
                return "createdTime";
            case "resolved":
                return "resolutionTime";
            default:
                return "id";

        }
    }

}
