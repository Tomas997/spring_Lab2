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
    public void deleteNewsById(Long id) {
        newsRepository.deleteNewsById(id);
    }

    @Override
    public Optional<News> getNewsById(Long id) {
        return newsRepository.getAllNews().stream()
                .filter(news -> news.getId().equals(id))
                .findFirst();
    }

    @Override
    public void addNews(News news) {
        newsRepository.addNews(news);
    }

    @Override
    public void updateNews(Long id, News updatedNews) {
        // Ensure the ID of the updatedNews matches the ID being updated
        updatedNews.setId(id); // Assuming setId exists in News
        newsRepository.updateNews(id, updatedNews);
    }
}
