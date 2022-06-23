package io.github.igormarti.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiErrors {

    @Getter
    private List<String> errors = new ArrayList<>();

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiErrors(String message){
        errors = Arrays.asList(message);
    }
}
