package com.example.lab2.services;

import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NewsService {
    Optional<List<News>> getAllNews();

    Optional<News> getNewsById(Long id);

    Optional<List<News>> getAllNewsByWord(String keywords);

    Optional<List<News>> getAllNewsByCategory(NewsCategory category);

    Map<NewsCategory, Integer> getNewsCountByCategory();

    Map<NewsCategory, String> getNewsCategoryStatus();

    News createNews(News news);

    News updateNews(Long id, News updatedNews);

    void deleteNews(Long id);

    Optional<List<News>> getNewsWithPaginationAndFiltering(String keywords, NewsCategory category, int page, int size);
}
