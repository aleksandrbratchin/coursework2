package ru.bratchin.coursework2.service.impl.java;

import org.junit.jupiter.api.*;
import ru.bratchin.coursework2.entity.Question;
import ru.bratchin.coursework2.exception.QuestionIsAlreadyPresentException;
import ru.bratchin.coursework2.exception.QuestionNotFoundException;
import ru.bratchin.coursework2.repository.impl.java.JavaQuestionRepository;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

/***
 * Без Мока
 */
@Disabled
class JavaQuestionServiceWithoutMockTest {

    private JavaQuestionService service;
    private JavaQuestionRepository repository;
    private static Field fieldQuestions;

    @BeforeAll
    public static void setup() throws NoSuchFieldException {
        fieldQuestions = JavaQuestionRepository.class.getDeclaredField("questions");
        fieldQuestions.setAccessible(true);
    }

    @Nested
    class AllSuccess {
        @BeforeEach
        public void initEach() throws IllegalAccessException {
            repository = new JavaQuestionRepository();
            service = new JavaQuestionService(repository);

            Set<Question> questions = new HashSet<>(
                    Set.of(
                            new Question("Какой метод запускает программу на Java?", "метод main"),
                            new Question("Чем является ключевое слово «public»?", "модификатором доступа"),
                            new Question("Для чего используется оператор NEW?", "для создания экземпляра класса")
                    )
            );

            fieldQuestions.set(repository, questions);
        }

        @Test
        void add() throws IllegalAccessException {
            Question question = new Question("Как называется оператор увеличения, который увеличивает значение переменной на единицу?", "инкремент");

            Question added = service.add(question.getQuestion(), question.getAnswer());
            var questions = (Set<Question>) fieldQuestions.get(repository);

            assertThat(added).isEqualTo(question);
            assertThat(questions.size()).isEqualTo(4);
            assertThat(questions).contains(question);
        }

        @Test
        void getAll() {

            Collection<Question> all = service.getAll();

            assertThat(all.size()).isEqualTo(3);
        }

        @Test
        void getRandomQuestion() throws IllegalAccessException {

            Question randomQuestion = service.getRandomQuestion();
            var questions = (Set<Question>) fieldQuestions.get(repository);

            assertThat(questions).contains(randomQuestion);
        }

        @Test
        void remove() throws IllegalAccessException {
            Question question = new Question("Какой метод запускает программу на Java?", "метод main");

            Question removed = service.remove(question);
            var questions = (Set<Question>) fieldQuestions.get(repository);

            assertThat(removed).isEqualTo(question);
            assertThat(questions.size()).isEqualTo(2);
            assertThat(questions).doesNotContain(question);
        }

    }

    @Nested
    class AllError {

        @BeforeEach
        public void initEach() {
            repository = new JavaQuestionRepository();
            service = new JavaQuestionService(repository);
        }

        @Test
        void add() throws IllegalAccessException {
            Question question = new Question("Какой метод запускает программу на Java?", "метод main");
            Set<Question> questions = new HashSet<>(
                    Set.of(question)
            );
            fieldQuestions.set(repository, questions);

            Throwable thrown = catchThrowable(() -> service.add(question.getQuestion(), question.getAnswer()));

            assertThat(thrown).isInstanceOf(QuestionIsAlreadyPresentException.class)
                    .hasMessageContaining(question.getQuestion());
        }

        @Test
        void remove() {
            Question question = new Question("Какой метод запускает программу на Java?", "метод main");

            Throwable thrown = catchThrowable(() -> service.remove(question));

            assertThat(thrown).isInstanceOf(QuestionNotFoundException.class)
                    .hasMessageContaining(question.getQuestion());
        }

        @Test
        void getRandomQuestion() {

            Question question = service.getRandomQuestion();

            assertThat(question).isNull();
        }

/*        @Test
        void getRandomQuestion() {

            Throwable thrown = catchThrowable(() -> service.getRandomQuestion());

            assertThat(thrown).isInstanceOf(QuestionNotFoundException.class);
        }*/

    }


}