package ru.bratchin.coursework2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MyIllegalArgumentException extends IllegalArgumentException {
    public MyIllegalArgumentException(String s) {
        super(s);
    }
}
