package com.example.lab2.controllers;

import com.example.lab2.models.News;
import com.example.lab2.services.NewsNotFoundException;
import com.example.lab2.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
@AllArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<String> createNews(@RequestBody News news) {
        newsService.createNews(news);
        return ResponseEntity.status(HttpStatus.CREATED).body("News created successfully.");
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
    public ResponseEntity<News> updateNews(@PathVariable Long id, @RequestBody News updatedNews) {
        updatedNews = newsService.updateNews(id, updatedNews);
        return ResponseEntity.ok(updatedNews);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }
}
