package com.revature.studyforce.flashcard.service;

import com.revature.studyforce.flashcard.model.Quiz;
import com.revature.studyforce.flashcard.repository.QuizRepository;
import com.revature.studyforce.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Locale;
import java.util.Optional;

/**
 * Services for the quiz repository {@link QuizRepository}
 */
@Service
public class QuizService {
    private final QuizRepository QUIZ_REPO;

    @Autowired
    public QuizService(QuizRepository quizRepository){
        this.QUIZ_REPO = quizRepository;
    }

    public Quiz createQuiz(Quiz newQuiz){
        return QUIZ_REPO.save(newQuiz);
    }

    public Page<Quiz> getAll(int page, int offset, String sortBy, String order){
        page = validatePage(page);
        offset = validateOffset(offset);
        sortBy = validateSortBy(sortBy);

        Page<Quiz> quizzes;
        if(order.equalsIgnoreCase("DESC"))
            quizzes = QUIZ_REPO.findAll(PageRequest.of(page, offset, Sort.by(sortBy).descending()));
        else
            quizzes = QUIZ_REPO.findAll(PageRequest.of(page, offset, Sort.by(sortBy).ascending()));

        return quizzes;

    }

    public Optional<Quiz> getById(Quiz quiz){
        return QUIZ_REPO.findById(quiz.getQuizId());
    }


    public Page<Quiz> getQuizzesByUserId(User user, Pageable pageable){
       return QUIZ_REPO.getQuizzesByUserId(user.getUserId(), pageable);
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
//        switch (sortBy.toLowerCase(Locale.ROOT)){
//            case "clockin":
//                return "clockIn";
//
//        }
        return null;
    }
}
