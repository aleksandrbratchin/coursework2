package ru.bratchin.coursework2.repository.impl.java;

import org.springframework.stereotype.Repository;
import ru.bratchin.coursework2.entity.Question;
import ru.bratchin.coursework2.exception.QuestionIsAlreadyPresentException;
import ru.bratchin.coursework2.exception.QuestionNotFoundException;
import ru.bratchin.coursework2.repository.api.QuestionRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public class JavaQuestionRepository implements QuestionRepository {

    private Set<Question> questions = new HashSet<>();

    @Override
    public Question add(Question question) {
        if (questions.add(question)) {
            return question;
        } else {
            throw new QuestionIsAlreadyPresentException(question);
        }
    }

    @Override
    public Question remove(Question question) {
        if (questions.remove(question)) {
            return question;
        } else {
            throw new QuestionNotFoundException(question);
        }
    }

    @Override
    public Collection<Question> getAll() {
        return questions;
    }

}
