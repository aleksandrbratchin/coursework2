package ru.bratchin.coursework2.service.api;

import ru.bratchin.coursework2.entity.Question;

import java.util.Collection;

public interface ExaminerService {

    Collection<Question> getQuestions(int amount);

}
