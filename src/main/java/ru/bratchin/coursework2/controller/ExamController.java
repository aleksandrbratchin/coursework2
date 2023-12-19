package ru.bratchin.coursework2.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bratchin.coursework2.service.api.ExaminerService;

@RestController
@RequestMapping("/exam")
public class ExamController {

    private final ExaminerService service;

    public ExamController(@Qualifier("examinerServiceImpl") ExaminerService service) {
        this.service = service;
    }

    @GetMapping("/get/{amount}")
    public ResponseEntity<?> getQuestions(
            @PathVariable Integer amount
    ) {
        return ResponseEntity.ok(service.getQuestions(amount));
    }

}
