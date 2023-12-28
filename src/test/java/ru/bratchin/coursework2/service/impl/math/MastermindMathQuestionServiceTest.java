package ru.bratchin.coursework2.service.impl.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.bratchin.coursework2.entity.Question;
import ru.bratchin.coursework2.exception.MethodNotAllowedException;
import ru.bratchin.coursework2.exception.MyIllegalArgumentException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Disabled
class MastermindMathQuestionServiceTest {

    private MastermindMathQuestionService service;

    Question question = new Question("5 * 5", "25");

    @BeforeEach
    void setUp() {
        service = new MastermindMathQuestionService();
    }

    @Test
    void add() {
        Throwable thrown = catchThrowable(() -> service.add(question.getQuestion(), question.getAnswer()));

        assertThat(thrown).isInstanceOf(MethodNotAllowedException.class);
    }

    @Test
    void remove() {
        Throwable thrown = catchThrowable(() -> service.remove(question));

        assertThat(thrown).isInstanceOf(MethodNotAllowedException.class);
    }

    @Test
    void getAll() {
        Throwable thrown = catchThrowable(() -> service.getAll());

        assertThat(thrown).isInstanceOf(MethodNotAllowedException.class);
    }

    @RepeatedTest(500)
    void getRandomQuestion() {
        String regex = "^(\\d+) ([+*/-]) (\\d+)$";
        Pattern pattern = Pattern.compile(regex);

        Question randomQuestion = service.getRandomQuestion();

        assertThat(randomQuestion.getQuestion()).matches(regex);
        Integer answer = Integer.valueOf(randomQuestion.getAnswer());
        Matcher matcher = pattern.matcher(randomQuestion.getQuestion());
        matcher.find();
        Integer operand1 = Integer.valueOf(matcher.group(1));
        Integer operand2 = Integer.valueOf(matcher.group(3));
        switch (matcher.group(2)) {
            case "+" -> assertThat(operand1 + operand2).isEqualTo(answer);
            case "-" -> assertThat(operand1 - operand2).isEqualTo(answer);
            case "/" -> assertThat(operand1 / operand2).isEqualTo(answer);
            case "*" -> assertThat(operand1 * operand2).isEqualTo(answer);
        }
    }

    @ParameterizedTest(name = "[{index}] - max = {0}")
    @CsvSource(
            value = {
                    "10",
                    "50",
                    "1000",
                    "1000000",
                    "2147483647",
            }
    )
    void getRandomQuestion(int max) {
        String regex = "^(\\d+) ([+*/-]) (\\d+)$";
        Pattern pattern = Pattern.compile(regex);

        Question randomQuestion = service.getRandomQuestion(max);

        assertThat(randomQuestion.getQuestion()).matches(regex);
        Integer answer = Integer.valueOf(randomQuestion.getAnswer());
        Matcher matcher = pattern.matcher(randomQuestion.getQuestion());
        matcher.find();
        Integer operand1 = Integer.valueOf(matcher.group(1));
        Integer operand2 = Integer.valueOf(matcher.group(3));
        switch (matcher.group(2)) {
            case "+" -> assertThat(operand1 + operand2).isEqualTo(answer);
            case "-" -> assertThat(operand1 - operand2).isEqualTo(answer);
            case "/" -> assertThat(operand1 / operand2).isEqualTo(answer);
            case "*" -> assertThat(operand1 * operand2).isEqualTo(answer);
        }
    }

    @ParameterizedTest(name = "[{index}] - max = {0}")
    @CsvSource(
            value = {
                    "9",
                    "0",
                    "-1"
            }
    )
    void getRandomQuestionError(int max) {

        Throwable thrown = catchThrowable(() -> service.getRandomQuestion(max));

        assertThat(thrown).isInstanceOf(MyIllegalArgumentException.class);
    }

}