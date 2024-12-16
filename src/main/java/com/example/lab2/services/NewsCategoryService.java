package com.example.lab2.services;

import com.example.lab2.dto.category.NewsCategoryDto;
import com.example.lab2.models.NewsCategory;

import java.util.List;

public interface NewsCategoryService {
    NewsCategory createCategory(NewsCategoryDto newsCategoryDto);
    List<NewsCategory> getAllCategories();
    NewsCategory getCategoryById(int id);
    NewsCategory updateCategory(int id,NewsCategoryDto newsCategoryDto);
    void deleteCategory(int id);
    NewsCategory getCategoryByName(String name);
}
