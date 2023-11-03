package ru.bratchin.coursework2.service.impl.math;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.bratchin.coursework2.entity.Question;
import ru.bratchin.coursework2.repository.api.QuestionRepository;
import ru.bratchin.coursework2.service.api.QuestionService;

import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
public class MathQuestionService implements QuestionService {

    private final QuestionRepository repository;

    public MathQuestionService(@Qualifier("mathQuestionRepository") QuestionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Question add(Question question) {
        return repository.add(question);
    }

    @Override
    public Question add(String question, String answer) {
        return add(new Question(question, answer));
    }

    @Override
    public Question remove(Question question) {
        return repository.remove(question);
    }

    @Override
    public Collection<Question> getAll() {
        return repository.getAll();
    }

    @Override
    public Question getRandomQuestion() {
        List<Question> all = getAll().stream().toList();
        return all.size() == 0 ? null :
                all.get(new Random().nextInt(all.size()));
    }

}
