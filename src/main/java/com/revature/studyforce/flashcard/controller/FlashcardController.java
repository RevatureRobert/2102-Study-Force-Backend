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
     * Retrieves a Page of flashcards {@link FlashcardService#getAll(int, int, String, String)}
     * @param page - number of offsets away from 0 (defaults to 0)
     * @param offset - number of elements per page [5|10|25|50|100] - defaults to 10
     * @param sortBy - column to sort by ["difficulty"|"topic"|"created"|"resolved"] defaults to creator if sortby could not be understood
     * @param order - order in which the Page is displayed ["ASC"|"DESC"]
     * @return - returns a Page of Flashcards according the the given page, offset, sortBy, and order parameters
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
     * Retrieves a Page of flashcards with a given difficulty {@link FlashcardService#getAllByDifficulty(int, int, String, String, int)}
     * @param page - number of offsets away from 0 (defaults to 0)
     * @param offset - number of elements per page [5|10|25|50|100] - defaults to 10
     * @param sortBy - column to sort by ["difficulty"|"topic"|"created"|"resolved"] defaults to creator if sortby could not be understood
     * @param order - order in which the Page is displayed ["ASC"|"DESC"]
     * @param difficulty - only return flashcards with the given difficulty
     * @return - returns a Page of Flashcards according the the given page, offset, sortBy, order, and difficulty parameters
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


    @GetMapping("/question")
    public @ResponseBody Page<FlashcardAllDTO> getAllByQuestionLike(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset,
            @RequestParam(name = "sortby", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "order", defaultValue = "desc", required = false) String order,
            @RequestParam(name = "question", required = false) String question) {
        return flashcardService.getAllByQuestionLike(page, offset, sortBy, order, question);
    }

    /**
     * Retrieves a Page of flashcards with a given topicName {@link FlashcardService#getAllByTopic(int, int, String, String, String)}
     * @param page - number of offsets away from 0 (defaults to 0)
     * @param offset - number of elements per page [5|10|25|50|100] - defaults to 10
     * @param sortBy - column to sort by ["difficulty"|"topic"|"created"|"resolved"] defaults to creator if sortby could not be understood
     * @param order - order in which the Page is displayed ["ASC"|"DESC"]
     * @param topicName - only return flashcards with the given topic name
     * @return - returns a Page of Flashcards according the the given page, offset, sortBy, order, and topicName parameters
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
     * Retrieves a Page of flashcards with a given resolved status (boolean) {@link FlashcardService#getAllByIsResolved(int, int, String, String, boolean)}
     * @param page - number of offsets away from 0 (defaults to 0)
     * @param offset - number of elements per page [5|10|25|50|100] - defaults to 10
     * @param sortBy - column to sort by ["difficulty"|"topic"|"created"|"resolved"] defaults to creator if sortby could not be understood
     * @param order - order in which the Page is displayed ["ASC"|"DESC"]
     * @param resolved - only return flashcards with the given resolved status [true|false]
     * @return - returns a Page of Flashcards according the the given page, offset, sortBy, order, and resolved parameters
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
     * Retrieves flashcard with the given id {@link FlashcardService#getById(int)}
     * @param id - limits returned Flashcard to the given id
     * @return - returns Flashcard with the given id
     */
    @GetMapping("/{id}")
    public @ResponseBody FlashcardAllDTO getById(@PathVariable("id") Integer id) {
        return flashcardService.getById(id);
    }

    /**
     * Retrieves flashcards with the given user id {@link FlashcardService#getByUserId(int)}
     * @param id - the id of the user
     * @return - returns Flashcards with the given user id
     */
    @GetMapping("by-user/{userId}")
    public @ResponseBody Page<FlashcardAllDTO> getByUserId(@PathVariable("userId") Integer id) {
        return flashcardService.getByUserId(id);
    }

    /**
     * Persists flashcard (uses NewFlashcardDTO) {@link FlashcardService#save(NewFlashcardDTO)}
     * @param flashcard - Flashcard object to persist
     * @return - returns persisted Flashcard
     */
    @PostMapping
    public @ResponseBody FlashcardDTO save(@RequestBody NewFlashcardDTO flashcard) {
        return flashcardService.save(flashcard);
    }

    /**
     * Updates existing flashcard {@link FlashcardService#update(Flashcard)}
     * @param flashcard - new Flashcard to replace original in database
     * @return - returns updated Flashcard
     */
    @PutMapping
    public @ResponseBody FlashcardDTO update(@RequestBody Flashcard flashcard) {
        return flashcardService.update(flashcard);
    }

    /**
     * Deletes existing flashcard with given id {@link FlashcardService#delete(int)}
     * @param id - Flashcard to be deleted from the database
     * @return - returns deletion success boolean
     */
    @DeleteMapping("/{id}")
    public @ResponseBody Boolean delete(@PathVariable("id") Integer id) {
        flashcardService.delete(id);
        return true;
    }
}
