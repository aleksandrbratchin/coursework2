package ru.bratchin.coursework2.repository.api;

import ru.bratchin.coursework2.entity.Question;

import java.util.Collection;

public interface QuestionRepository {
    Question add(Question question);

    Question remove(Question question);

    Collection<Question> getAll();

}
