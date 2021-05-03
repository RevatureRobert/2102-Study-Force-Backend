package com.revature.studyforce.flashcard.controller;

import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.repository.FlashcardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * Flashcard controller
 */

@Controller
@CrossOrigin
@RequestMapping("/flashcards")
public class FlashcardController {

    @Autowired
    FlashcardRepo repo;

    /**
     * getAll() method mapped to HTTP GET requests ("/all")
     * @param page - number of offsets away from 0
     * @param offset - number of Flashcards per page
     * @return - returns Page of paginated Flashcards
     */
    @GetMapping("/all")
    public @ResponseBody Page<Flashcard> getAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset) {
        return repo.findAll(PageRequest.of(page, offset));
    }

    /**
     * getAllByDifficulty() method mapped to HTTP GET requests ("/difficulty")
     * @param page - number of offsets away from 0
     * @param offset - number of Flashcards per offset
     * @param difficulty - limits returned Flashcards to the given difficulty
     * @return - returns a List of paginated Flashcards sorted by difficulty
     */
    @GetMapping("/difficulty")
    public @ResponseBody List<Flashcard> getAllByDifficulty(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset,
            @RequestParam(name = "difficulty", required = true) int difficulty) {
        return repo.findALlByQuestionDifficultyTotal(difficulty, PageRequest.of(page, offset));
    }

    /**
     * getById() method mapped to HTTP GET requests ("/id/{id}")
     * @param id - limits returned Flashcard to the given id
     * @return - returns Flashcard with the given id
     */
    @GetMapping("/id/{id}")
    public @ResponseBody Flashcard getById(@PathParam("id") int id) {
        return repo.findById(id).get();
    }

    /**
     * save() method mapped to HTTP POST requests
     * @param flashcard - Flashcard object to persist
     * @return - returns persisted Flashcard
     */
    @PostMapping
    public @ResponseBody Flashcard save(@RequestBody Flashcard flashcard) {
        return repo.save(flashcard);
    }

    /**
     * update() method mapped to HTTP PUT requests
     * @param flashcard - new Flashcard to replace original in database
     * @return - returns updated Flashcard
     */
    @PutMapping
    public @ResponseBody Flashcard update(@RequestBody Flashcard flashcard) {
        Flashcard original = repo.getOne(flashcard.getId());

        int id = original.getId();
        original = flashcard;
        original.setId(id);

        return repo.save(original);
    }

    /**
     * delete() method mapped to HTTP DELETE requests
     * @param flashcard - Flashcard to be deleted from the database
     * @return - returns deleted Flashcard
     */
    @DeleteMapping
    public @ResponseBody Flashcard delete(@RequestBody Flashcard flashcard) {
        repo.delete(flashcard);
        return flashcard;
    }
}
