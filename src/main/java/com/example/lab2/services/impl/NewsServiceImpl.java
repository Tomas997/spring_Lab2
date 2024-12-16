package com.example.lab2.services.impl;


import com.example.lab2.dto.NewsDto;
import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import com.example.lab2.repositories.NewsRepository;
import com.example.lab2.services.NewsNotFoundException;
import com.example.lab2.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public News createNews(NewsDto news) {
        return newsRepository.createNews(news);
    }

    @Override
    public Optional<List<News>> getAllNews() {
        return newsRepository.getAllNews();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<News> getNewsById(Long id) {
        return newsRepository.getAllNews()
                .orElse(Collections.emptyList())
                .stream()
                .filter(news -> news.getId().equals(id))
                .findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<NewsCategory, String> getNewsCategoryStatus() {
        // Отримуємо список новин
        List<News> newsList = newsRepository.getAllNews()
                .orElseThrow(NewsNotFoundException::new);

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

    @Override
    @Transactional
    public News updateNews(Long id, NewsDto updatedNews) {
        Optional<News> updated = newsRepository.updateNews(id, updatedNews);
        if (updated.isEmpty()) {
            throw new NewsNotFoundException(id);
        }
        return updated.get();
    }


    @Override
    public void deleteNews(Long id) {
        boolean removed = newsRepository.deleteNewsById(id);
        if (!removed) {
            throw new NewsNotFoundException(id);
        }
    }

    @Override
    public Optional<List<News>> getNewsWithPaginationAndFiltering(String keywords, NewsCategory category, int page, int size) {
        return newsRepository.getAllNews().map(newsList -> {
            Stream<News> stream = newsList.stream();

            if (keywords != null && !keywords.isEmpty()) {
                stream = stream.filter(news -> news.getTitle().contains(keywords) || news.getContent().contains(keywords));
            }

            if (category != null) {
                stream = stream.filter(news -> news.getCategory() == category);
            }

            return stream
                    .skip((long) page * size)
                    .limit(size)
                    .toList();
        });
    }

    @Override
    @Transactional
    public void deleteAllNewsByCategory(NewsCategory category) {
        Optional<List<News>> newsToDelete = newsRepository.getAllNewsByCategory(category);

        if (!newsToDelete.isEmpty()) {
            for (News news : newsToDelete.get()) {
                newsRepository.deleteNewsById(news.getId());
            }
        }
    }
}

