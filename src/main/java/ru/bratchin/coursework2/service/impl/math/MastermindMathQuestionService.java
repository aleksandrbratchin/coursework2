package ru.bratchin.coursework2.service.impl.math;

import org.springframework.stereotype.Service;
import ru.bratchin.coursework2.entity.Question;
import ru.bratchin.coursework2.exception.MethodNotAllowedException;
import ru.bratchin.coursework2.exception.MyIllegalArgumentException;
import ru.bratchin.coursework2.service.api.QuestionService;

import java.util.Collection;
import java.util.Random;

@Service
public class MastermindMathQuestionService implements QuestionService {

    @Override
    public Question add(Question question) {
        throw new MethodNotAllowedException();
    }

    @Override
    public Question add(String question, String answer) {
        throw new MethodNotAllowedException();
    }

    @Override
    public Question remove(Question question) {
        throw new MethodNotAllowedException();
    }

    @Override
    public Collection<Question> getAll() {
        throw new MethodNotAllowedException();
    }

    @Override
    public Question getRandomQuestion() {
        return getRandomQuestion(100);
    }

    public Question getRandomQuestion(Integer max) {
        if (max < 10) {
            throw new MyIllegalArgumentException("max должен быть больше или равен 10");
        }
        int operand1;
        int operand2;
        Random random = new Random();
        int operation = random.nextInt(4);
        return switch (operation) {
            case 0 -> {
                operand1 = random.nextInt(max / 2);
                operand2 = random.nextInt(max / 2);
                yield new Question(
                        operand1 + " + " + operand2,
                        String.valueOf(operand1 + operand2)
                );
            }
            case 1 -> {
                operand1 = random.nextInt(max);
                operand2 = random.nextInt(operand1 / 2 + 1);
                yield new Question(
                        operand1 + " - " + operand2,
                        String.valueOf(operand1 - operand2)
                );
            }
            case 2 -> {
                Integer answer = random.nextInt(max);
                operand2 = random.nextInt(answer / 2 + 1) + 1;
                operand1 = answer / operand2;
                answer = operand2 * operand1;
                yield new Question(
                        operand1 + " * " + operand2,
                        String.valueOf(answer)
                );
            }
            default -> {
                int answer = random.nextInt(max / 10 + 1);
                operand2 = random.nextInt(max / 10 + 1) + 1;
                operand1 = answer * operand2;
                yield new Question(
                        operand1 + " / " + operand2,
                        String.valueOf(operand1 / operand2)
                );
            }

        };

    }

}
