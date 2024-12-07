package com.example.lab2.controllers.exception;

import com.example.lab2.services.NewsNotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@RestControllerAdvice
public class GlobalExceptionalHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NewsNotFoundException.class)
    public ProblemDetail handleProductNotFoundException(NewsNotFoundException ex) {
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("news-not-found"));
        problemDetail.setTitle("News Not Found");
        return problemDetail;
    }
}
