package com.example.lab2.services;

public class NewsCategoryNotFoundException extends RuntimeException{
    public NewsCategoryNotFoundException(Long id) {
        super(String.format("Category with id %s not found", id));
    }
    public NewsCategoryNotFoundException() {
        super("Category not found");
    }
}