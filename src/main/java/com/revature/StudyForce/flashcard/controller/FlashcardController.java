package com.revature.StudyForce.flashcard.controller;

import com.revature.StudyForce.flashcard.dto.FlashcardDTO;
import com.revature.StudyForce.flashcard.model.Flashcard;
import com.revature.StudyForce.flashcard.repository.FlashcardRepository;
import com.revature.StudyForce.flashcard.service.FlashcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Controller for handling Flashcards
 * @author Luke
 */
@Controller
@CrossOrigin
@RequestMapping("/flashcards")
public class FlashcardController {

    @Autowired
    FlashcardService flashcardService;

    /**
     * getAll() method mapped to HTTP GET requests ("/all")
     * @param page - number of offsets away from 0
     * @param offset - number of Flashcards per page
     * @param sortBy - column to sort by
     * @param order - ascending or descending order
     * @return - returns Page of paginated Flashcards
     */
    @GetMapping("/all")
    public @ResponseBody Page<FlashcardDTO> getAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset,
            @RequestParam(name = "sortby", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "order", defaultValue = "desc", required = false) String order) {
        return flashcardService.getAll(page, offset, sortBy, order);
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
    @GetMapping("/difficulty")
    public @ResponseBody Page<FlashcardDTO> getAllByDifficulty(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset,
            @RequestParam(name = "sortby", defaultValue = "id", required = false) String sortBy,
            @RequestParam(name = "order", defaultValue = "desc", required = false) String order,
            @RequestParam(name = "difficulty", required = false) Integer difficulty) {
        return flashcardService.getAllByDifficulty(page, offset, sortBy, order, difficulty);
    }

    /**
     * getById() method mapped to HTTP GET requests ("/id/{id}")
     * @param id - limits returned Flashcard to the given id
     * @return - returns Flashcard with the given id
     */
    @GetMapping("/id/{id}")
    public @ResponseBody FlashcardDTO getById(@PathVariable("id") Integer id) {
        return flashcardService.getById(id);
    }

    /**
     * save() method mapped to HTTP POST requests
     * @param flashcard - Flashcard object to persist
     * @return - returns persisted Flashcard
     */
    @PostMapping
    public @ResponseBody FlashcardDTO save(@RequestBody Flashcard flashcard) {
        return flashcardService.save(flashcard);
    }

    /**
     * update() method mapped to HTTP PUT requests
     * @param flashcard - new Flashcard to replace original in database
     * @return - returns updated Flashcard
     */
    @PutMapping
    public @ResponseBody FlashcardDTO update(@RequestBody Flashcard flashcard) {
        return flashcardService.update(flashcard);
    }

    /**
     * delete() method mapped to HTTP DELETE requests
     * @param flashcard - Flashcard to be deleted from the database
     * @return - returns deleted Flashcard
     */
    @DeleteMapping
    public @ResponseBody FlashcardDTO delete(@RequestBody Flashcard flashcard) {
        return flashcardService.delete(flashcard);
    }
}
