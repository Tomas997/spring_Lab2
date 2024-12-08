package com.example.lab2.repositories.impl;

import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import com.example.lab2.repositories.NewsRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class StubNewsRepository implements NewsRepository {
    private final List<News> newsList = new ArrayList<>(Arrays.asList(
            new News(1L, "Спортивні новини", "Огляд останніх спортивних подій", NewsCategory.SPORTS, LocalDate.parse("2024-10-20")),
            new News(2L, "Політика", "Нові закони в Україні", NewsCategory.POLITICS, LocalDate.parse("2024-10-19")),
            new News(3L, "Технології", "Новинки в світі технологій", NewsCategory.TECHNOLOGY, LocalDate.parse("2024-10-18"))
    ));

    private long nextId = 4L;

    @Override
    public Optional<List<News>> getAllNews() {
        return newsList.isEmpty() ? Optional.empty() : Optional.of(newsList);
    }

    @Override
    public Optional<List<News>> getAllNewsByCategory(NewsCategory category) {
        List<News> filteredNews = newsList.stream()
                .filter(news -> news.getCategory().equals(category))
                .toList();

        return filteredNews.isEmpty() ? Optional.empty() : Optional.of(filteredNews);
    }

    @Override
    public Optional<List<News>> getAllNewsByWord(String keywords) {
        List<News> filteredNews = newsList.stream()
                .filter(news -> news.getTitle().toLowerCase().contains(keywords.toLowerCase()) ||
                        news.getContent().toLowerCase().contains(keywords.toLowerCase()))
                .toList();

        return filteredNews.isEmpty() ? Optional.empty() : Optional.of(filteredNews);
    }

    @Override
    public Map<NewsCategory, Integer> getNewsCountByCategory() {
        // Ініціалізація мапи з усіма категоріями та дефолтними значеннями 0
        Map<NewsCategory, Integer> categoryCountMap = Arrays.stream(NewsCategory.values())
                .collect(Collectors.toMap(category -> category, category -> 0));

        // Підрахунок новин для кожної категорії
        newsList.stream()
                .collect(Collectors.groupingBy(
                        News::getCategory,
                        Collectors.summingInt(news -> 1)
                ))
                .forEach(categoryCountMap::put);

        return categoryCountMap;
    }



    @Override
    public void createNews(News news) {
        news.setId(nextId);
        nextId++;
        newsList.add(news);
    }
}


