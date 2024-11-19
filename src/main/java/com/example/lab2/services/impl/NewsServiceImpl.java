package com.example.lab2.services.impl;


import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import com.example.lab2.repositories.NewsRepository;
import com.example.lab2.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Map<NewsCategory, Integer> getNewsCountByCategory() {
        return newsRepository.getNewsCountByCategory();
    }


    @Override
    public void createNews(News news) {
        newsRepository.createNews(news);
    }

    @Override
    public Optional<List<News>> getAllNews() {
        return newsRepository.getAllNews();
    }

    @Override
    public Map<NewsCategory, String> getNewsCategoryStatus() {
        // Отримуємо список новин
        List<News> newsList = newsRepository.getAllNews().orElseThrow(() -> new IllegalStateException("No news found"));

        // Групуємо за категорією та визначаємо статус для кожної категорії
        Map<NewsCategory, Integer> categoryCounts = newsList.stream()
                .collect(Collectors.groupingBy(
                        News::getCategory,
                        Collectors.summingInt(news -> 1)
                ));

        // Встановлюємо статуси для кожної категорії
        return categoryCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            int count = entry.getValue();
                            if (count < 5) {
                                return "low";
                            } else if (count <= 10) {
                                return "medium";
                            } else {
                                return "high";
                            }
                        }
                ));
    }

}
