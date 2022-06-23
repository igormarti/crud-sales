package io.github.igormarti.rest.controller;

import io.github.igormarti.exception.NaoEncontradoException;
import io.github.igormarti.exception.RegraNegocioException;
import io.github.igormarti.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ApiErrors> handleRegraNegocioException(RegraNegocioException ex){
        ApiErrors errors = new ApiErrors(ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(NaoEncontradoException.class)
    public ResponseEntity<ApiErrors> handlerNaoEncontradoException(NaoEncontradoException ex){
        ApiErrors errors = new ApiErrors(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrors> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ApiErrors errors = new ApiErrors(
                ex.getBindingResult().getAllErrors().stream()
                        .map(objectError -> objectError.getDefaultMessage())
                        .collect(Collectors.toList())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
