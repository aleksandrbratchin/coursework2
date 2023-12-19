package ru.bratchin.coursework2.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.bratchin.coursework2.entity.Question;
import ru.bratchin.coursework2.exception.MyIllegalArgumentException;
import ru.bratchin.coursework2.service.api.ExaminerService;
import ru.bratchin.coursework2.service.api.QuestionService;

import java.util.*;
import java.util.stream.IntStream;

@Service
public class ExaminerServiceImpl implements ExaminerService {

    public List<QuestionService> services;

    public ExaminerServiceImpl(
            @Qualifier("javaQuestionService") QuestionService javaService,
            @Qualifier("mathQuestionService") QuestionService mathService) {
        services = new ArrayList<>();
        this.services.add(javaService);
        this.services.add(mathService);
    }


    @Override
    public Collection<Question> getQuestions(int amount) {
        Set<Question> questions = new HashSet<>();
        int maxQuestions = services.stream()
                .flatMapToInt(questionService -> IntStream.of(questionService.getAll().size()))
                .sum();
        if (maxQuestions < amount) throw new MyIllegalArgumentException("Нет такого количества вопросов");
        while (questions.size() < amount) {
            Question randomQuestion;
            do {
                QuestionService randomService = services.get(new Random().nextInt(services.size()));
                randomQuestion = randomService.getRandomQuestion();
            } while (randomQuestion == null);
            questions.add(randomQuestion);

        }
        return questions;
    }


}
