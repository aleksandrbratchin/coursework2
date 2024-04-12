package ru.bratchin.coursework2.service.api;

import ru.bratchin.coursework2.entity.Question;

import java.util.Collection;

public interface QuestionService {

    Question add(Question question);

    Question add(String question, String answer);

    Question remove(Question question);

    Collection<Question> getAll(); //todo почему Collection

    Question getRandomQuestion();

}
