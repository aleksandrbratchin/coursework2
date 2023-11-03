package ru.bratchin.coursework2.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.bratchin.coursework2.entity.Question;
import ru.bratchin.coursework2.repository.impl.java.JavaQuestionRepository;
import ru.bratchin.coursework2.repository.impl.math.MathQuestionRepository;
import ru.bratchin.coursework2.service.impl.ExaminerServiceImpl;
import ru.bratchin.coursework2.service.impl.math.MathQuestionService;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ExamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExamController controller;
    @Autowired
    private ExaminerServiceImpl service;
    @Autowired
    private MathQuestionRepository mathQuestionRepository;
    @Autowired
    private MathQuestionService mathQuestionService;
    @Autowired
    private JavaQuestionController javaQuestionController;
    @Autowired
    private JavaQuestionRepository javaQuestionRepository;


    @Nested
    class AllSuccess {

        @BeforeEach
        public void initEach() throws NoSuchFieldException, IllegalAccessException {
            Field fieldJavaQuestions = JavaQuestionRepository.class.getDeclaredField("questions");
            fieldJavaQuestions.setAccessible(true);
            Field fieldMathQuestions = MathQuestionRepository.class.getDeclaredField("questions");
            fieldMathQuestions.setAccessible(true);

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

            fieldJavaQuestions.set(javaQuestionRepository, javaQuestions);
            fieldMathQuestions.set(mathQuestionRepository, mathQuestions);
        }

        @Test
        void getQuestions() throws Exception {

            mockMvc.perform(get("/exam/get/{amount}", "6")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            /*.andExpect(jsonPath("$[0].name", is("bob")))*/;

        }

    }


}