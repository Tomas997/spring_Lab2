package com.example.lab2.controllers;

import com.example.lab2.dto.NewsDto;
import com.example.lab2.dto.exception.MyValidationException;
import com.example.lab2.entity.News;
import com.example.lab2.services.NewsCategoryService;
import com.example.lab2.services.exeption.NewsNotFoundException;
import com.example.lab2.services.NewsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.FieldError;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/news")
@AllArgsConstructor
public class NewsController {

    private final NewsService newsService;
    private final NewsCategoryService newsCategoryService;


    @PostMapping
    public ResponseEntity<News> createNews(@RequestBody @Valid NewsDto news, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            Map<String, String> errors = bindingResult.getFieldErrors().stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getDefaultMessage
                    ));

            throw new MyValidationException(errors);
        }
        News newsCreated = newsService.createNews(news);
        return ResponseEntity.status(HttpStatus.CREATED).body(newsCreated);
    }


    @GetMapping
    public ResponseEntity<List<News>> getAllNews() {
        return newsService.getAllNews()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable Long id) {
        News news = newsService.getNewsById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));
        return ResponseEntity.ok(news);
    }

    @PutMapping("/{id}")
    public ResponseEntity<News> updateNews(@PathVariable Long id, @RequestBody @Valid NewsDto updatedNews) {
        try {
            News news = newsService.updateNews(id, updatedNews);
            return ResponseEntity.ok(news);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }

    @DeleteMapping("/category/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllNewsByCategory(@PathVariable int id) {
        try {
            newsService.deleteAllNewsByCategory(id);
        }catch (Exception e) {}

    }
}
