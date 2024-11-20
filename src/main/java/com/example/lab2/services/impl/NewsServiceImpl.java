package com.example.lab2.services.impl;


import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import com.example.lab2.repositories.NewsRepository;
import com.example.lab2.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
        List<News> newsList = newsRepository.getAllNews()
                .orElseThrow(() -> new IllegalStateException("No news found"));

        // Групуємо новини за категоріями та рахуємо кількість публікацій для кожної категорії
        Map<NewsCategory, Integer> categoryCounts = newsList.stream()
                .collect(Collectors.groupingBy(
                        News::getCategory,
                        Collectors.summingInt(news -> 1)
                ));

        double averageCount = categoryCounts.values().stream()
                .mapToInt(count -> count)
                .average()
                .orElse(0);
        int maxCount = categoryCounts.values().stream()
                .mapToInt(count -> count)
                .max()
                .orElse(0);

        // Встановлюємо статуси для кожної категорії
        return Arrays.stream(NewsCategory.values())
                .collect(Collectors.toMap(
                        category -> category,
                        category -> {
                            int count = categoryCounts.getOrDefault(category, 0);
                            if (count == 0) {
                                return "no"; // Немає новин
                            } else if (count < averageCount) {
                                return "low"; // Менше середнього
                            } else if (count < maxCount) {
                                return "medium"; // Між середнім і максимумом
                            } else {
                                return "high"; // Максимальне значення
                            }
                        }
                ));
    }



}
