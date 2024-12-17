package com.example.lab2.repositories;


import com.example.lab2.dto.NewsDto;
import com.example.lab2.entity.News;
import com.example.lab2.entity.NewsCategory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NewsRepository {
    Optional<List<News>> getAllNews();

    Optional<List<News>> getAllNewsByCategory(NewsCategory category);

    Optional<List<News>> getAllNewsByWord(String word);

    Map<NewsCategory, Integer> getNewsCountByCategory();

    News createNews(NewsDto news);

    boolean deleteNewsById(Long id);

    Optional<News> updateNews(Long id, NewsDto updatedNews);
}
