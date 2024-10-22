package com.example.lab2.repositories;


import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;

import java.util.List;
import java.util.Optional;

public interface NewsRepository {
    List<News> getAllNews();
    Optional<List<News>> getAllNewsByCategory(NewsCategory category);
    Optional<List<News>> getAllNewsByWord(String word);
    void deleteNewsById(Long id);

    void addNews(News news);

    void updateNews(Long id, News updatedNews);
}
