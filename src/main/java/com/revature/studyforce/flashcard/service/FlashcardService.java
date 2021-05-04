package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.dto.FlashcardAllDTO;
import com.revature.studyforce.flashcard.dto.FlashcardDTO;
import com.revature.studyforce.flashcard.dto.NewFlashcardDTO;
import com.revature.studyforce.flashcard.model.Difficulty;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Topic;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.repository.TopicRepository;
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
 * Services for the Flashcard repository
 * @author Luke
 */
@Service
public class FlashcardService extends AbstractService {

    private final FlashcardRepository flashcardRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Autowired
    public FlashcardService(TopicRepository topicRepository, FlashcardRepository flashcardRepository, UserRepository userRepository){
        this.flashcardRepository =flashcardRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
    }

    /**
     * getAll() method mapped to HTTP GET requests ("/all")
     * @param page - number of offsets away from 0
     * @param offset - number of Flashcards per page
     * @param sortBy - column to sort by
     * @param order - ascending or descending order
     * @return - returns Page of paginated Flashcards
     */
    public Page<FlashcardAllDTO> getAll(int page, int offset, String sortBy, String order){
        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Flashcard> flashcards;

    if (order.equalsIgnoreCase("DESC")) {
      flashcards = flashcardRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).descending()));
    } else {
      flashcards = flashcardRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
    }

        return flashcards.map(FlashcardAllDTO.convertToDTO());
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
    public Page<FlashcardAllDTO> getAllByDifficulty(int page, int offset, String sortBy, String order, int difficulty) {
        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Flashcard> flashcards;

        if (order.equalsIgnoreCase("DESC")) {
            flashcards = flashcardRepository.findAllByQuestionDifficultyTotal(difficulty, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        } else {
            flashcards = flashcardRepository.findAllByQuestionDifficultyTotal(difficulty, PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        }

        return flashcards.map(FlashcardAllDTO.convertToDTO());
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
    public Page<FlashcardAllDTO> getAllByTopic(int page, int offset, String sortBy, String order, String topicName) {
        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Flashcard> flashcards;

        if (order.equalsIgnoreCase("DESC")) {
            flashcards = flashcardRepository.findAllByTopicTopicName(topicName, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        } else {
            flashcards = flashcardRepository.findAllByTopicTopicName(topicName, PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        }

        return flashcards.map(FlashcardAllDTO.convertToDTO());
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
    public Page<FlashcardAllDTO> getAllByIsResolved(int page, int offset, String sortBy, String order, boolean resolved) {
        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Flashcard> flashcards;

        if (order.equalsIgnoreCase("DESC")) {
            flashcards = flashcardRepository.findAllByIsResolved(resolved, PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        } else {
            flashcards = flashcardRepository.findAllByIsResolved(resolved, PageRequest.of(page, offset, Sort.by(sortBy).ascending()));
        }

        return flashcards.map(FlashcardAllDTO.convertToDTO());
    }

    /**
     * getById() method mapped to HTTP GET requests ("/id/{id}")
     * @param id - limits returned Flashcard to the given id
     * @return - returns Flashcard with the given id
     */
    public FlashcardAllDTO getById(int id) {
        return FlashcardAllDTO.convertToDTO().apply(flashcardRepository.findById(id).orElse(null));
    }

    /**
     * save() method mapped to HTTP POST requests
     * @param flashcard - Flashcard object to persist
     * @return - returns persisted Flashcard
     */
    public FlashcardDTO save(NewFlashcardDTO flashcard) {
        Optional<User> optUser = userRepository.findById(flashcard.getUserId());
        Optional<Topic> optTopic = topicRepository.findById(flashcard.getTopicID());
        Difficulty difficulty = Difficulty.fromInteger(flashcard.getDifficulty());

        if(!optUser.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found exception");
        if(!optTopic.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Topic not found exception");
        if(difficulty==null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Difficulty should be a number from 1 to 3");

        Flashcard f = new Flashcard(0,
                optUser.get(),
                optTopic.get(),
                flashcard.getQuestion(),
                difficulty.ordinal(),
                difficulty.ordinal(),
                Timestamp.valueOf(LocalDateTime.now()),
                null,
                false);
        return FlashcardDTO.convertToDTO().apply(flashcardRepository.save(f));
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
     * @param id - Flashcard id to be deleted from the database
     */
    public void delete(int id) {
        flashcardRepository.deleteById(id);
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
