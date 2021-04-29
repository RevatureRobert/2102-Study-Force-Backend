package com.revature.StudyForce.controller;

import com.revature.StudyForce.model.Flashcard;
import com.revature.StudyForce.repository.FlashcardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/flashcards")
public class FlashcardController {

    @Autowired
    FlashcardRepo repo;

    @GetMapping("/all")
    public @ResponseBody Page<Flashcard> getAll(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset) {
        return repo.findAll(PageRequest.of(page, offset));
    }

    @GetMapping("/difficulty")
    public @ResponseBody List<Flashcard> getAllByDifficulty(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "offset", defaultValue = "10", required = false) int offset,
            @RequestParam(name = "difficulty", required = true) int difficulty) {
        return repo.findALlByQuestionDifficultyTotal(difficulty, PageRequest.of(page, offset));
    }

    @GetMapping("/id/{id}")
    public @ResponseBody Flashcard getById(@PathParam("id") int id) {
        return repo.findById(id).get();
    }

    @PostMapping
    public @ResponseBody Flashcard save(@RequestBody Flashcard flashcard) {
        return repo.save(flashcard);
    }

    @PutMapping
    public @ResponseBody Flashcard update(@RequestBody Flashcard flashcard) {
        Flashcard original = repo.getOne(flashcard.getId());

        int id = original.getId();
        original = flashcard;
        original.setId(id);

        return repo.save(original);
    }

    @DeleteMapping
    public @ResponseBody Flashcard delete(@RequestBody Flashcard flashcard) {
        repo.delete(flashcard);
        return flashcard;
    }
}
