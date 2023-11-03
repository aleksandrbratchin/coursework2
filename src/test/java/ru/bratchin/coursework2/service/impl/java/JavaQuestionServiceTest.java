package ru.bratchin.coursework2.service.impl.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.bratchin.coursework2.entity.Question;
import ru.bratchin.coursework2.repository.impl.java.JavaQuestionRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class JavaQuestionServiceTest {

    private JavaQuestionService service;

    @MockBean
    private JavaQuestionRepository repository;

    @BeforeEach
    void setUp() {
        service = new JavaQuestionService(repository);
    }

    @Nested
    class AllSuccess {

        Set<Question> questions = new HashSet<>(
                Set.of(
                        new Question("Какой метод запускает программу на Java?", "метод main"),
                        new Question("Чем является ключевое слово «public»?", "модификатором доступа"),
                        new Question("Для чего используется оператор NEW?", "для создания экземпляра класса")
                )
        );
        Question question = new Question("Как называется оператор увеличения, который увеличивает значение переменной на единицу?", "инкремент");

        @Test
        void add() {
            Mockito.when(repository.add(question))
                    .thenReturn(question);

            Question added = service.add(question.getQuestion(), question.getAnswer());

            assertThat(added).isEqualTo(question);
        }

        @Test
        void getAll() {
            Mockito.when(repository.getAll())
                    .thenReturn(questions);

            Collection<Question> all = service.getAll();

            assertThat(all.size()).isEqualTo(3);
        }

        @Test
        void remove() {
            Mockito.when(repository.remove(question))
                    .thenReturn(question);

            Question removed = service.remove(question);

            assertThat(removed).isEqualTo(question);
        }

        @Test
        void getRandomQuestion() {
            Mockito.when(repository.getAll())
                    .thenReturn(questions);

            Question question = service.getRandomQuestion();

            assertThat(question).isIn(questions);
        }

    }

    @Nested
    class AllError {
        @Test
        void getRandomQuestion() {
            Mockito.when(repository.getAll())
                    .thenReturn(Set.of());

            Question question = service.getRandomQuestion();

            assertThat(question).isNull();
        }
    }

}