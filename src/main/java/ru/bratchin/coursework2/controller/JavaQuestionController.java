package ru.bratchin.coursework2.controller;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bratchin.coursework2.entity.Question;
import ru.bratchin.coursework2.service.api.QuestionService;

@RestController
@RequestMapping("/exam/java")
@Validated
public class JavaQuestionController {

    private final QuestionService service;

    public JavaQuestionController(@Qualifier("javaQuestionService") QuestionService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/add")
    public ResponseEntity<?> add(
            @RequestParam @NotBlank String question,
            @RequestParam @NotBlank String answer
    ) {
        Question employee = new Question(question, answer);
        return ResponseEntity.ok(service.add(employee));
    }

    @GetMapping("/remove")
    public ResponseEntity<?> remove(
            @RequestParam @NotBlank String question,
            @RequestParam @NotBlank String answer
    ) {
        Question employee = new Question(question, answer);
        return ResponseEntity.ok(service.remove(employee));
    }
}
