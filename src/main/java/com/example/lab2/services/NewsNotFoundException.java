package com.example.lab2.services;


public class NewsNotFoundException extends RuntimeException {

    public NewsNotFoundException(Long id) {
        super(String.format("News with id %s not found", id));
    }
    public NewsNotFoundException() {
        super("News not found");
    }
}