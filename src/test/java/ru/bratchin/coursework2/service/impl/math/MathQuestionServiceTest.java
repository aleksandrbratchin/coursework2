package ru.bratchin.coursework2.service.impl.math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.bratchin.coursework2.entity.Question;
import ru.bratchin.coursework2.repository.impl.math.MathQuestionRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class MathQuestionServiceTest {

    private MathQuestionService service;

    @Mock
    private MathQuestionRepository repository;

    @BeforeEach
    void setUp() {
        service = new MathQuestionService(repository);
    }

    @Nested
    class AllSuccess {

        Set<Question> questions = new HashSet<>(
                Set.of(
                        new Question("2 + 2", "5"),
                        new Question("5 * 5", "25"),
                        new Question("49 / 7", "7")
                )
        );
        Question question = new Question("8 * 7", "56");

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

        @Nested
        class RandomQuestion {
            @Test
            void repositoryIsNotEmpty() {
                Mockito.when(repository.getAll())
                        .thenReturn(questions);

                Question question = service.getRandomQuestion();

                assertThat(question).isIn(questions);
            }

            @Test
            void repositoryIsEmpty() {
                Mockito.when(repository.getAll())
                        .thenReturn(Set.of());

                Question question = service.getRandomQuestion();

                assertThat(question).isNull();
            }
        }

    }

}