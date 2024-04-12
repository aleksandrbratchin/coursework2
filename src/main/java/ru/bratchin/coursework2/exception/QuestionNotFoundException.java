package ru.bratchin.coursework2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.bratchin.coursework2.entity.Question;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(Question question) {
        super("Вопрос \"" + question.getQuestion() + "\" не удалось найти!");
    }

}
