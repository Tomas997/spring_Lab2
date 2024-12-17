package com.example.lab2.controllers.exception;

import com.example.lab2.dto.exception.MyValidationException;
import com.example.lab2.services.exeption.NewsCategoryNotFoundException;
import com.example.lab2.services.exeption.NewsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@RestControllerAdvice
public class GlobalExceptionalHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NewsNotFoundException.class)
    public ProblemDetail handleNewsNotFoundException(NewsNotFoundException ex) {
        ProblemDetail problemDetail = forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("news-not-found"));
        problemDetail.setTitle("News Not Found");
        return problemDetail;
    }

    @ExceptionHandler(NewsCategoryNotFoundException.class)
    public ProblemDetail handleNewsCategoryNotFoundException(NewsCategoryNotFoundException ex) {
        ProblemDetail problemDetail = forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("category-not-found"));
        problemDetail.setTitle("Category Not Found");
        return problemDetail;
    }

    @ExceptionHandler(MyValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MyValidationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("errors", ex.getValidationErrors());
        response.put("timestamp", LocalDateTime.now());

        return ResponseEntity.badRequest().body(response);
    }
}


