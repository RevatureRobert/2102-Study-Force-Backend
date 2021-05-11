package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.dto.NewQuizDTO;
import com.revature.studyforce.flashcard.dto.QuizDTO;
import com.revature.studyforce.flashcard.dto.UpdateQuizDTO;
import com.revature.studyforce.flashcard.model.Flashcard;
import com.revature.studyforce.flashcard.model.Quiz;
import com.revature.studyforce.flashcard.repository.FlashcardRepository;
import com.revature.studyforce.flashcard.repository.QuizRepository;
import com.revature.studyforce.user.model.User;
import com.revature.studyforce.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Services for the quiz repository {@link QuizRepository}
 * @author Nick Zimmerman
 */
@Service
public class QuizService implements AbstractService{
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final FlashcardRepository flashcardRepository;


    @Autowired
    public QuizService(QuizRepository quizRepository, UserRepository userRepository, FlashcardRepository flashcardRepository){
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.flashcardRepository = flashcardRepository;
    }


    /**
     * Http GET request that queries the configured database for a pageable list of all quizzes from {@link QuizRepository#findAll()}
     * @param page the page number
     * @param offset number of entries per page
     * @param sortBy attribute to sort by
     * @param order asc or desc
     * @return page of QuizDTO entries to be displayed
     */
    public Page<QuizDTO> getAll(int page, int offset, String sortBy, String order){
        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Quiz> quizzes;
        if(order.equalsIgnoreCase("DESC"))
            quizzes = quizRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        else
            quizzes = quizRepository.findAll(PageRequest.of(page, offset, Sort.by(sortBy).ascending()));

        return quizzes.map(QuizDTO.quizToDTO());

    }

    /**
     * Queries the quiz with specified id from {@link QuizRepository#findById(Object)}
     * @param quizId quiz id of the quiz object to retrieve
     * @return Optional with quiz object if it was found
     */
    public QuizDTO getById(int quizId){
        return QuizDTO.quizToDTO().apply(quizRepository.findById(quizId).orElse(null));
    }

    /**
     * Create a new Quiz object and save's into {@link QuizRepository#save(Object)}
     * @param newQuiz - the Quiz object to be persisted
     * @return new QuizDTO
     */
    public QuizDTO createQuiz(NewQuizDTO newQuiz){
        Optional<User> optUser = userRepository.findById(newQuiz.getQuizUserId());

        if(!optUser.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found exception");

        List<Flashcard> lf = new ArrayList<>();

        newQuiz.getFlashcardsId().forEach(id -> {
            Optional<Flashcard> optionalFlashcard = flashcardRepository.findById(id);

            if(!optionalFlashcard.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Flashcard not found exception");

            lf.add(optionalFlashcard.get());
        });

        return  QuizDTO.quizToDTO().apply(quizRepository.save(new Quiz(0, optUser.get(), newQuiz.getQuizName(), lf)));
    }


    /**
     * Update fields of a Quiz object in {@link QuizRepository}
     * @param quizDTO - quizDTO for Quiz object being mutated
     * @return the mutated QuizDTO object
     */
    public QuizDTO updateQuiz(UpdateQuizDTO quizDTO) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizDTO.getQuizId());
        Optional<User> optionalUser = userRepository.findById(quizDTO.getQuizUserId());

        if(!optionalQuiz.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Quiz not found exception");
        if(!optionalUser.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"User not found exception");

        List<Flashcard> lf = new ArrayList<>();

        quizDTO.getFlashcardsId().forEach(id -> {
            Optional<Flashcard> optionalFlashcard = flashcardRepository.findById(id);

            if(!optionalFlashcard.isPresent())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Flashcard not found exception");

            lf.add(optionalFlashcard.get());
        });

        Quiz quiz = optionalQuiz.get();
        quiz.setQuizName(quizDTO.getQuizName());
        quiz.setQuizUser(optionalUser.get());
        quiz.setFlashcards(lf);

        return  QuizDTO.quizToDTO().apply(quizRepository.save(quiz));
    }


    /**
     * Delete a Quiz object in {@link QuizRepository}
     * @param id - the quizDTO id to be deleted
     */
    public void deleteQuiz(int id) {
        quizRepository.deleteById(id);
    }


    /**
     * Ensures permitted sortby format
     * @param sortBy The sortby value being validated
     * @return A valid sortby value
     */
    @Override
    public String validateSortBy(String sortBy){
        switch (sortBy.toLowerCase(Locale.ROOT)){
            case "quizid":
                return "quizId";
            case "quizuserid":
                return "quizUserId";
            case "quizname":
                return "quizName";
            default:
                return "quizId";
        }
    }
}
