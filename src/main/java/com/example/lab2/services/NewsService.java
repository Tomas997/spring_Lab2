package com.example.lab2.services;

import com.example.lab2.dto.NewsDto;
import com.example.lab2.entity.News;
import com.example.lab2.entity.NewsCategory;

import java.util.List;
import java.util.Optional;

public interface NewsService {
    Optional<List<News>> getAllNews();

    Optional<News> getNewsById(Long id);

    Optional<List<News>> getAllNewsByWord(String keywords);

    Optional<List<News>> getAllNewsByCategory(NewsCategory category);

    News createNews(NewsDto news);

    News updateNews(Long id, NewsDto updatedNews);

    void deleteNews(Long id);

    void deleteAllNewsByCategory(int id);
}

