package com.example.lab2.services.impl;


import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import com.example.lab2.repositories.NewsRepository;
import com.example.lab2.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    @Override
    public Optional<List<News>> getAllNewsByWord(String keywords) {
        return newsRepository.getAllNewsByWord(keywords);
    }

    @Override
    public Optional<List<News>> getAllNewsByCategory(NewsCategory category) {
        return newsRepository.getAllNewsByCategory(category);
    }



    @Override
    public void createNews(News news) {
        newsRepository.createNews(news);
    }

    @Override
    public Optional<List<News>> getAllNews() {
        return newsRepository.getAllNews();
    }
}
