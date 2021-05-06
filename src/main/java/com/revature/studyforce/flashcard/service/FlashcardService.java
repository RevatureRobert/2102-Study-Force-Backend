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
 * Services for the Flashcard repository {@link FlashcardRepository}
 * @author Luke Mohr
 */
@Service
public class FlashcardService implements AbstractService {

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
     * Retrieves a Page of flashcards
     * @param page - number of offsets away from 0 (defaults to 0)
     * @param offset number of elements per page [5|10|25|50|100] - defaults to 25
     * @param sortBy - column to sort by ["difficulty"|"topic"|"created"|"resolved"] defaults to creator if sortby could not be understood
     * @param order - order in which the Page is displayed ["ASC"|"DESC"]
     * @return - returns a Page of Flashcards according the the given page, offset, sortBy, and order parameters
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
     * Retrieves a Page of flashcards with a given difficulty
     * @param page - number of offsets away from 0 (defaults to 0)
     * @param offset number of elements per page [5|10|25|50|100] - defaults to 25
     * @param sortBy - column to sort by ["difficulty"|"topic"|"created"|"resolved"] defaults to creator if sortby could not be understood
     * @param order - order in which the Page is displayed ["ASC"|"DESC"]
     * @param difficulty - only return flashcards with the given difficulty
     * @return - returns a Page of Flashcards according the the given page, offset, sortBy, order, and difficulty parameters
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
     * Retrieves a Page of flashcards with a given topicName
     * @param page - number of offsets away from 0 (defaults to 0)
     * @param offset number of elements per page [5|10|25|50|100] - defaults to 25
     * @param sortBy - column to sort by ["difficulty"|"topic"|"created"|"resolved"] defaults to creator if sortby could not be understood
     * @param order - order in which the Page is displayed ["ASC"|"DESC"]
     * @param topicName - only return flashcards with the given topic name
     * @return - returns a Page of Flashcards according the the given page, offset, sortBy, order, and topicName parameters
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
     * Retrieves a Page of flashcards with a given resolved status (boolean)
     * @param page - number of offsets away from 0 (defaults to 0)
     * @param offset number of elements per page [5|10|25|50|100] - defaults to 25
     * @param sortBy - column to sort by ["difficulty"|"topic"|"created"|"resolved"] defaults to creator if sortby could not be understood
     * @param order - order in which the Page is displayed ["ASC"|"DESC"]
     * @param resolved - only return flashcards with the given resolved status [true|false]
     * @return - returns a Page of Flashcards according the the given page, offset, sortBy, order, and resolved parameters
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
     * Retrieves flashcard with the given id
     * @param id - limits returned Flashcard to the given id
     * @return - returns Flashcard with the given id
     */
    public FlashcardAllDTO getById(int id) {
        return FlashcardAllDTO.convertToDTO().apply(flashcardRepository.findById(id).orElse(null));
    }

    /**
     * Persists flashcard (uses NewFlashcardDTO)
     * @param flashcard - Flashcard object to persist
     * @return - returns persisted Flashcard
     */
    public FlashcardDTO save(NewFlashcardDTO flashcard) {
        Optional<User> optUser = userRepository.findById(flashcard.getUserId());
        Optional<Topic> optTopic = topicRepository.findById(flashcard.getTopicId());
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
     * Updates existing flashcard
     * @param flashcard - new Flashcard to replace original in database
     * @return - returns updated Flashcard
     */
    public FlashcardDTO update(Flashcard flashcard) {
        Flashcard original = flashcardRepository.findById(flashcard.getId()).orElse(null);

        if (original == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Flashcard not found exception");
        }

        int id = original.getId();
        original = flashcard;
        original.setId(id);

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
    public String validateSortBy(String sortBy){
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
