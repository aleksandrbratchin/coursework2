package ru.bratchin.coursework2.service.impl;

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
            List<QuestionService> services
    ) {
        this.services = services;
    }


    @Override
    public Collection<Question> getQuestions(int amount) {
        Set<Question> questions = new HashSet<>();
        int maxQuestions = services.stream()
                .flatMapToInt(questionService -> IntStream.of(questionService.getAll().size()))
                .sum();
        if (maxQuestions < amount) throw new MyIllegalArgumentException("Нет такого количества вопросов");
        Question randomQuestion;
        QuestionService randomService;
        Random random = new Random();
        while (questions.size() < amount) {
            do {
                randomService = services.get(random.nextInt(services.size()));
                randomQuestion = randomService.getRandomQuestion();
            } while (randomQuestion == null);
            questions.add(randomQuestion);
        }
        return questions;
    }


}
