package ru.bratchin.coursework2.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.bratchin.coursework2.entity.Question;
import ru.bratchin.coursework2.exception.MyIllegalArgumentException;
import ru.bratchin.coursework2.service.impl.java.JavaQuestionService;
import ru.bratchin.coursework2.service.impl.math.MathQuestionService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
class ExaminerServiceImplTest {

    private ExaminerServiceImpl service;
    @Mock
    private JavaQuestionService javaService;
    @Mock
    private MathQuestionService mathService;

    @BeforeEach
    void setUp() {
        service = new ExaminerServiceImpl(List.of(javaService, mathService));
    }

    @Nested
    class AllSuccess {
        @RepeatedTest(5)
        void getQuestions() {
            Question javaQuestion = new Question("Какой метод запускает программу на Java?", "main");
            Question mathQuestion = new Question("49 / 7", "7");
            Mockito.when(javaService.getRandomQuestion())
                    .thenReturn(javaQuestion);
            Mockito.when(javaService.getAll())
                    .thenReturn(Set.of(javaQuestion));
            Mockito.when(mathService.getRandomQuestion())
                    .thenReturn(mathQuestion);
            Mockito.when(mathService.getAll())
                    .thenReturn(Set.of(mathQuestion));

            Set<Question> questions = new HashSet<>(service.getQuestions(2));

            assertThat(questions.size()).isEqualTo(2);
            assertThat(questions).containsAll(Set.of(mathQuestion, javaQuestion));
        }

        @ParameterizedTest
        @CsvSource(
                {
                        "-1",
                        "0"
                }
        )
        void incorrectAmount(int amount) {
            Question javaQuestion = new Question("Какой метод запускает программу на Java?", "main");
            Question mathQuestion = new Question("49 / 7", "7");
            Mockito.when(javaService.getRandomQuestion())
                    .thenReturn(javaQuestion);
            Mockito.when(javaService.getAll())
                    .thenReturn(Set.of(javaQuestion));
            Mockito.when(mathService.getRandomQuestion())
                    .thenReturn(mathQuestion);
            Mockito.when(mathService.getAll())
                    .thenReturn(Set.of(mathQuestion));

            Set<Question> questions = new HashSet<>(service.getQuestions(amount));

            assertThat(questions.size()).isEqualTo(0);
            assertThat(questions).isEmpty();
        }

        @RepeatedTest(5)
        void getQuestionsJavaQuestionsIsEmpty() {
            Question mathQuestion = new Question("49 / 7", "7");
            Mockito.when(javaService.getRandomQuestion())
                    .thenReturn(null);
            Mockito.when(javaService.getAll())
                    .thenReturn(Set.of());
            Mockito.when(mathService.getRandomQuestion())
                    .thenReturn(mathQuestion);
            Mockito.when(mathService.getAll())
                    .thenReturn(Set.of(mathQuestion));

            Set<Question> questions = new HashSet<>(service.getQuestions(1));

            assertThat(questions.size()).isEqualTo(1);
            assertThat(questions).contains(mathQuestion);
        }

    }

    @Nested
    class AllError {

        @Test
        void getQuestionsJavaQuestionsIsEmpty() {
            Mockito.when(javaService.getRandomQuestion())
                    .thenReturn(null);
            Mockito.when(javaService.getAll())
                    .thenReturn(Set.of());
            Mockito.when(mathService.getRandomQuestion())
                    .thenReturn(null);
            Mockito.when(mathService.getAll())
                    .thenReturn(Set.of());

            Throwable thrown = catchThrowable(() -> service.getQuestions(1));

            assertThat(thrown).isInstanceOf(MyIllegalArgumentException.class);
        }

    }

}