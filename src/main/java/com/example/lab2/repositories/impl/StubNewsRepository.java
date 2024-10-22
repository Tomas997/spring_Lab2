package com.example.lab2.repositories.impl;

import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import com.example.lab2.repositories.NewsRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class StubNewsRepository implements NewsRepository {
    private final List<News> newsList = new ArrayList<>(Arrays.asList(
            new News(1L, "Спортивні новини", "Огляд останніх спортивних подій", NewsCategory.SPORTS, LocalDate.parse("2024-10-20")),
            new News(2L, "Політика", "Нові закони в Україні", NewsCategory.POLITICS, LocalDate.parse("2024-10-19")),
            new News(3L, "Технології", "Новинки в світі технологій", NewsCategory.TECHNOLOGY, LocalDate.parse("2024-10-18"))
    ));

    private long nextId = 4L; // Лічильник для наступного ID

    @Override
    public List<News> getAllNews() {
        return newsList;
    }

    @Override
    public Optional<List<News>> getAllNewsByCategory(NewsCategory category) {
        List<News> filteredNews = newsList.stream()
                .filter(news -> news.getCategory().equals(category))
                .collect(Collectors.toList());

        return filteredNews.isEmpty() ? Optional.empty() : Optional.of(filteredNews);
    }

    @Override
    public Optional<List<News>> getAllNewsByWord(String keywords) {
        List<News> filteredNews = newsList.stream()
                .filter(news -> news.getTitle().toLowerCase().contains(keywords.toLowerCase()) ||
                        news.getContent().toLowerCase().contains(keywords.toLowerCase()))
                .collect(Collectors.toList());

        return filteredNews.isEmpty() ? Optional.empty() : Optional.of(filteredNews);
    }

    @Override
    public void deleteNewsById(Long id) {
        newsList.removeIf(news -> news.getId().equals(id));
    }

    @Override
    public void addNews(News news) {
        // Встановлюємо унікальний ID для нової новини
        news.setId(nextId);
        nextId++; // Збільшуємо лічильник для наступного ID
        newsList.add(news);
    }

    @Override
    public void updateNews(Long id, News updatedNews) {
        for (int i = 0; i < newsList.size(); i++) {
            News news = newsList.get(i);
            if (news.getId().equals(id)) {
                newsList.set(i, updatedNews); // Заміна старої новини новою
                return;
            }
        }
    }
}

