package com.example.lab2.controllers;

import com.example.lab2.dto.category.NewsCategoryDto;
import com.example.lab2.dto.category.NewsCategoryResponseDto;
import com.example.lab2.entity.NewsCategory;
import com.example.lab2.services.NewsCategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@AllArgsConstructor
public class NewsCategoryController {

    private final NewsCategoryService newsCategoryService;

    @GetMapping
    public ResponseEntity<List<NewsCategory>> getAllCategories() {
        List<NewsCategory> categories = newsCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{id}")
    public ResponseEntity<NewsCategory> getCategoryById(@PathVariable int id) {
        return ResponseEntity.ok(newsCategoryService.getCategoryById(id));
    }

    @PostMapping
    public ResponseEntity<NewsCategoryResponseDto> createCategory(@RequestBody NewsCategoryDto categoryDto) {
        NewsCategory category = newsCategoryService.createCategory(categoryDto);
        NewsCategoryResponseDto newsCategoryResponseDto = NewsCategoryResponseDto.builder()
                .id(category.getId())
                .displayName(category.getDisplayName())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(newsCategoryResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsCategory> updateCategory(@PathVariable int id, @RequestBody NewsCategoryDto categoryDto) {
        return ResponseEntity.ok(newsCategoryService.updateCategory(id,categoryDto));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable int id) {
        newsCategoryService.deleteCategory(id);
    }
}