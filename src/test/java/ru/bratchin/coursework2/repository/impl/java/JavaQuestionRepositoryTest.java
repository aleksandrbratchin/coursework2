package ru.bratchin.coursework2.repository.impl.java;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.bratchin.coursework2.entity.Question;
import ru.bratchin.coursework2.exception.QuestionIsAlreadyPresentException;
import ru.bratchin.coursework2.exception.QuestionNotFoundException;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class JavaQuestionRepositoryTest {

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

            Question added = repository.add(question);
            var questions = (Set<Question>) fieldQuestions.get(repository);

            assertThat(added).isEqualTo(question);
            assertThat(questions.size()).isEqualTo(4);
            assertThat(questions).contains(question);
        }

        @Test
        void getAll() {

            Collection<Question> all = repository.getAll();

            assertThat(all.size()).isEqualTo(3);
        }

        @Test
        void remove() throws IllegalAccessException {
            Question question = new Question("Какой метод запускает программу на Java?", "метод main");

            Question removed = repository.remove(question);
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
        }

        @Test
        void add() throws IllegalAccessException {
            Question question = new Question("Какой метод запускает программу на Java?", "метод main");
            Set<Question> questions = new HashSet<>(
                    Set.of(question)
            );
            fieldQuestions.set(repository, questions);

            Throwable thrown = catchThrowable(() -> repository.add(question));

            assertThat(thrown).isInstanceOf(QuestionIsAlreadyPresentException.class)
                    .hasMessageContaining(question.getQuestion());
        }

        @Test
        void remove() {
            Question question = new Question("Какой метод запускает программу на Java?", "метод main");

            Throwable thrown = catchThrowable(() -> repository.remove(question));

            assertThat(thrown).isInstanceOf(QuestionNotFoundException.class)
                    .hasMessageContaining(question.getQuestion());
        }

    }

}