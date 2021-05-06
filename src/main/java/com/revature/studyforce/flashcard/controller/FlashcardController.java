package com.revature.studyforce.flashcard.controller;

import com.revature.studyforce.flashcard.dto.FlashcardAllDTO;
import com.revature.studyforce.flashcard.dto.FlashcardDTO;
import com.revature.studyforce.flashcard.dto.NewFlashcardDTO;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling Flashcards using {@link FlashcardService}
 * @author Luke Mohr
 */
@Controller
@CrossOrigin
@RequestMapping("/flashcards")
public class FlashcardController {


    private final FlashcardService flashcardService;

    @Autowired
    public FlashcardController(FlashcardService flashcardService){
        this.flashcardService = flashcardService;
    }

    /**
     * Retrieves all flashcards
     * @param page - number of offsets away from 0
     * @param offset - number of Flashcards per page
     * @param sortBy - column to sort by
     * @param order - ascending or descending order
     * @return - returns Page of paginated Flashcards
     */
    @GetMapping
    public @ResponseBody Page<FlashcardAllDTO> getAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset,
            @RequestParam(name = "sortby", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "order", defaultValue = "desc", required = false) String order) {
        return flashcardService.getAll(page, offset, sortBy, order);
    }

    /**
     * Retrieves all flashcards with the given difficulty
     * @param page - number of offsets away from 0
     * @param offset - number of Flashcards per offset
     * @param sortBy - column to sort by
     * @param order - ascending or descending order
     * @param difficulty - limits returned Flashcards to the given difficulty
     * @return - returns a Page of Flashcards by difficulty
     */
    @GetMapping("/difficulty")
    public @ResponseBody Page<FlashcardAllDTO> getAllByDifficulty(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset,
            @RequestParam(name = "sortby", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "order", defaultValue = "desc", required = false) String order,
            @RequestParam(name = "difficulty", required = false) Integer difficulty) {
        return flashcardService.getAllByDifficulty(page, offset, sortBy, order, difficulty);
    }

    /**
     * Retrieves all flashcards with the given topic
     * @param page - number of offsets away from 0
     * @param offset - number of Flashcards per offset
     * @param sortBy - column to sort by
     * @param order - ascending or descending order
     * @param topicName - limits returned Flashcards to the given topic
     * @return - returns a Page of Flashcards by topic
     */
    @GetMapping("/topics")
    public @ResponseBody Page<FlashcardAllDTO> getAllByTopic(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset,
            @RequestParam(name = "sortby", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "order", defaultValue = "desc", required = false) String order,
            @RequestParam(name = "topicName", required = false) String topicName) {
        return flashcardService.getAllByTopic(page, offset, sortBy, order, topicName);
    }

    /**
     * Retrieves all flashcards with the given resolved status (boolean)
     * @param page - number of offsets away from 0
     * @param offset - number of Flashcards per offset
     * @param sortBy - column to sort by
     * @param order - ascending or descending order
     * @param resolved - limits returned Flashcards to the given resolved boolean
     * @return - returns a Page of Flashcards by resolved status
     */
    @GetMapping("/resolved")
    public @ResponseBody Page<FlashcardAllDTO> getAllByIsResolved(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset,
            @RequestParam(name = "sortby", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "order", defaultValue = "desc", required = false) String order,
            @RequestParam(name = "resolved", required = false) Boolean resolved) {
        return flashcardService.getAllByIsResolved(page, offset, sortBy, order, resolved);
    }

    /**
     * Retrieves flashcard by id
     * @param id - limits returned Flashcard to the given id
     * @return - returns Flashcard with the given id
     */
    @GetMapping("/{id}")
    public @ResponseBody FlashcardAllDTO getById(@PathVariable("id") Integer id) {
        return flashcardService.getById(id);
    }

    /**
     * Persists flashcard (uses NewFlashcardDTO)
     * @param flashcard - Flashcard object to persist
     * @return - returns persisted Flashcard
     */
    @PostMapping
    public @ResponseBody FlashcardDTO save(@RequestBody NewFlashcardDTO flashcard) {
        return flashcardService.save(flashcard);
    }

    /**
     * Updates existing flashcard
     * @param flashcard - new Flashcard to replace original in database
     * @return - returns updated Flashcard
     */
    @PutMapping
    public @ResponseBody FlashcardDTO update(@RequestBody Flashcard flashcard) {
        return flashcardService.update(flashcard);
    }

    /**
     * Deletes existing flashcard with given id
     * @param id - Flashcard to be deleted from the database
     * @return - returns deletion success boolean
     */
    @DeleteMapping("/{id}")
    public @ResponseBody Boolean delete(@PathVariable("id") Integer id) {
        flashcardService.delete(id);
        return true;
    }
}
