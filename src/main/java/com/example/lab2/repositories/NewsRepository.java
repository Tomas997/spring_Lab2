package com.example.lab2.repositories;


import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface NewsRepository {
    Optional<List<News>> getAllNews();
    Optional<List<News>> getAllNewsByCategory(NewsCategory category);
    Optional<List<News>> getAllNewsByWord(String word);
    Map<NewsCategory, Integer> getNewsCountByCategory();
    void createNews(News news);


}
