package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.dto.QuizDTO;
import com.revature.studyforce.flashcard.model.Quiz;
import com.revature.studyforce.flashcard.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Locale;
import java.util.Optional;

/**
 * Services for the quiz repository {@link QuizRepository}
 */
@Service
public class QuizService {
    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository){
        this.quizRepository = quizRepository;
    }



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

    public Optional<Quiz> getById(Quiz quiz){
        return quizRepository.findById(quiz.getQuizId());
    }

    /**
     * Create a new Quiz object
     * @param newQuiz - the Quiz object to be persisted
     * @return new QuizDTO
     */
    public QuizDTO createQuiz(Quiz newQuiz){
        Quiz saved = quizRepository.save(newQuiz);
        return QuizDTO.quizToDTO().apply(saved);
    }


    /**
     * Update a Quiz object
     * @param quizDTO - quizDTO for Quiz object being mutated
     * @return the mutated QuizDTO object
     */
    public QuizDTO updateQuiz(QuizDTO quizDTO) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizDTO.getQuizId());

        if(!optionalQuiz.isPresent())
            return null;

        Quiz quiz = optionalQuiz.get();
        quiz.setQuizName(quizDTO.getQuizName());
        quiz.setQuizUser(quizDTO.getQuizUser());
        quiz.setFlashcards(quizDTO.getFlashcards());

        return  QuizDTO.quizToDTO().apply(quizRepository.save(quiz));
    }


    /**
     * Delete a Quiz object
     * @param quizDTO - the quizDTO to be deleted
     */
    public void deleteQuiz(QuizDTO quizDTO) {
        quizRepository.deleteById(quizDTO.getQuizId());
    }

    /**
     * Ensures permitted offset format
     * @param offset The offset value being validated
     * @return A valid offset value
     */
    private int validateOffset(int offset){
        if(offset != 5 && offset != 10 && offset != 25 && offset != 50 && offset != 100)
            offset = 25;
        return offset;
    }

    /**
     * Ensures permitted page format
     * @param page The page number value being validated
     * @return A valid page number value
     */
    private int validatePage(int page){
        if(page < 0)
            page = 0;
        return page;
    }

    /**
     * Ensures permitted sortby format
     * @param sortBy The sortby value being validated
     * @return A valid sortby value
     */
    private String validateSortBy(String sortBy){
        switch (sortBy.toLowerCase(Locale.ROOT)){
            case "quizid":
                return "quizId";

        }

        return null;
    }
}
