package ru.bratchin.coursework2.service.impl;

import org.junit.jupiter.api.*;
import ru.bratchin.coursework2.entity.Question;
import ru.bratchin.coursework2.exception.MyIllegalArgumentException;
import ru.bratchin.coursework2.repository.impl.java.JavaQuestionRepository;
import ru.bratchin.coursework2.repository.impl.math.MathQuestionRepository;
import ru.bratchin.coursework2.service.impl.java.JavaQuestionService;
import ru.bratchin.coursework2.service.impl.math.MathQuestionService;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Disabled
class ExaminerServiceImplWithoutMockTest {

    private ExaminerServiceImpl service;
    private JavaQuestionRepository javaRepository;
    private JavaQuestionService javaService;
    private MathQuestionRepository mathRepository;
    private MathQuestionService mathService;
    private static Field fieldJavaQuestions;
    private static Field fieldMathQuestions;

    @BeforeAll
    public static void setup() throws NoSuchFieldException {
        fieldJavaQuestions = JavaQuestionRepository.class.getDeclaredField("questions");
        fieldJavaQuestions.setAccessible(true);
        fieldMathQuestions = MathQuestionRepository.class.getDeclaredField("questions");
        fieldMathQuestions.setAccessible(true);
    }

    @Nested
    class AllSuccess {

        @BeforeEach
        public void initEach() throws IllegalAccessException {
            javaRepository = new JavaQuestionRepository();
            javaService = new JavaQuestionService(javaRepository);
            mathRepository = new MathQuestionRepository();
            mathService = new MathQuestionService(mathRepository);
            service = new ExaminerServiceImpl(List.of(javaService, mathService));

            Set<Question> javaQuestions = new HashSet<>(
                    Set.of(
                            new Question("Какой метод запускает программу на Java?", "метод main"),
                            new Question("Чем является ключевое слово «public»?", "модификатором доступа"),
                            new Question("Для чего используется оператор NEW?", "для создания экземпляра класса")
                    )
            );

            Set<Question> mathQuestions = new HashSet<>(
                    Set.of(
                            new Question("2 + 2", "5"),
                            new Question("5 * 5", "25"),
                            new Question("49 / 7", "7")
                    )
            );

            fieldJavaQuestions.set(javaRepository, javaQuestions);
            fieldMathQuestions.set(mathRepository, mathQuestions);
        }

        @Test
        void getQuestions() {

            Set<Question> questions = new HashSet<>(service.getQuestions(6));

            assertThat(questions.size()).isEqualTo(6);

        }

    }

    @Nested
    class AllError {

        @BeforeEach
        public void initEach() throws IllegalAccessException {
            javaRepository = new JavaQuestionRepository();
            javaService = new JavaQuestionService(javaRepository);
            mathRepository = new MathQuestionRepository();
            mathService = new MathQuestionService(mathRepository);
            service = new ExaminerServiceImpl(List.of(javaService, mathService));

            Set<Question> javaQuestions = new HashSet<>(
                    Set.of(
                            new Question("Какой метод запускает программу на Java?", "метод main"),
                            new Question("Чем является ключевое слово «public»?", "модификатором доступа"),
                            new Question("Для чего используется оператор NEW?", "для создания экземпляра класса")
                    )
            );

            Set<Question> mathQuestions = new HashSet<>(
                    Set.of(
                            new Question("2 + 2", "5"),
                            new Question("5 * 5", "25"),
                            new Question("49 / 7", "7")
                    )
            );

            fieldJavaQuestions.set(javaRepository, javaQuestions);
            fieldMathQuestions.set(mathRepository, mathQuestions);
        }

        @Test
        void getQuestions() {

            Throwable thrown = catchThrowable(() -> service.getQuestions(7));

            assertThat(thrown).isInstanceOf(MyIllegalArgumentException.class);
        }

    }

}