package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.dto.FlashcardDTO;
import com.revature.studyforce.flashcard.model.Flashcard;
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

    private final FlashcardRepository flashcardRepository;

    @Autowired
    public FlashcardService(FlashcardRepository flashcardRepository){
        this.flashcardRepository =flashcardRepository;
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
      flashcards = flashcardRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).descending()));
    } else {
      flashcards = flashcardRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
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
            flashcards = flashcardRepository.findAllByQuestionDifficultyTotal(difficulty, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        } else {
            flashcards = flashcardRepository.findAllByQuestionDifficultyTotal(difficulty, PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        }

        return flashcards.map(FlashcardDTO.convertToDTO());
    }

    /**
     * getAllByTopic() method mapped to HTTP GET requests ("/difficulty")
     * @param page - number of offsets away from 0
     * @param offset - number of Flashcards per offset
     * @param sortBy - column to sort by
     * @param order - ascending or descending order
     * @param topicName - limits returned Flashcards to the given topic
     * @return - returns a List of paginated Flashcards sorted by topic
     */
    public Page<FlashcardDTO> getAllByTopic(int page, int offset, String sortBy, String order, String topicName) {
        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Flashcard> flashcards;

        if (order.equalsIgnoreCase("DESC")) {
            flashcards = flashcardRepository.findAllByTopicTopicName(topicName, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        } else {
            flashcards = flashcardRepository.findAllByTopicTopicName(topicName, PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        }

        return flashcards.map(FlashcardDTO.convertToDTO());
    }

    /**
     * getAllByIsResolved() method mapped to HTTP GET requests ("/difficulty")
     * @param page - number of offsets away from 0
     * @param offset - number of Flashcards per offset
     * @param sortBy - column to sort by
     * @param order - ascending or descending order
     * @param resolved - limits returned Flashcards to the given resolved status
     * @return - returns a List of paginated Flashcards sorted by resolved status
     */
    public Page<FlashcardDTO> getAllByIsResolved(int page, int offset, String sortBy, String order, boolean resolved) {
        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Flashcard> flashcards;

        if (order.equalsIgnoreCase("DESC")) {
            flashcards = flashcardRepository.findAllByIsResolved(resolved, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        } else {
            flashcards = flashcardRepository.findAllByIsResolved(resolved, PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        }

        return flashcards.map(FlashcardDTO.convertToDTO());
    }

    /**
     * getById() method mapped to HTTP GET requests ("/id/{id}")
     * @param id - limits returned Flashcard to the given id
     * @return - returns Flashcard with the given id
     */
    public FlashcardDTO getById(int id) {
        return FlashcardDTO.convertToDTO().apply(flashcardRepository.findById(id).orElse(null));
    }

    /**
     * save() method mapped to HTTP POST requests
     * @param flashcard - Flashcard object to persist
     * @return - returns persisted Flashcard
     */
    public FlashcardDTO save(Flashcard flashcard) {
        return FlashcardDTO.convertToDTO().apply(flashcardRepository.save(flashcard));
    }

    /**
     * update() method mapped to HTTP PUT requests
     * @param flashcard - new Flashcard to replace original in database
     * @return - returns updated Flashcard
     */
    public FlashcardDTO update(Flashcard flashcard) {
        Flashcard original = flashcardRepository.findById(flashcard.getId()).orElse(null);

        if (original != null) {
            int id = original.getId();
            original = flashcard;
            original.setId(id);
        }
        assert original != null;
        return FlashcardDTO.convertToDTO().apply(flashcardRepository.save(original));
    }

    /**
     * delete() method mapped to HTTP DELETE requests
     * @param flashcard - Flashcard to be deleted from the database
     * @return - returns deleted Flashcard
     */
    public @ResponseBody
    FlashcardDTO delete(@RequestBody Flashcard flashcard) {
        flashcardRepository.delete(flashcard);
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
