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
            // Спорт - середня кількість
            new News(1L, "Спортивні новини", "Огляд останніх спортивних подій", NewsCategory.SPORTS, LocalDate.parse("2024-10-20")),
            new News(2L, "Футбол", "Шахтар виграв чемпіонат", NewsCategory.SPORTS, LocalDate.parse("2024-10-15")),
            new News(3L, "Олімпіада", "Україна здобула золото", NewsCategory.SPORTS, LocalDate.parse("2024-09-30")),

            // Політика - мала кількість
            new News(4L, "Політика", "Нові закони в Україні", NewsCategory.POLITICS, LocalDate.parse("2024-10-19")),
            new News(5L, "Вибори", "Оголошено дату виборів", NewsCategory.POLITICS, LocalDate.parse("2024-09-25")),

            // Технології - багато новин
            new News(6L, "Технології", "Новинки в світі технологій", NewsCategory.TECHNOLOGY, LocalDate.parse("2024-10-18")),
            new News(7L, "AI", "Новий прорив у штучному інтелекті", NewsCategory.TECHNOLOGY, LocalDate.parse("2024-10-10")),
            new News(8L, "Гаджети", "Огляд нового смартфона", NewsCategory.TECHNOLOGY, LocalDate.parse("2024-10-01")),
            new News(9L, "Космос", "Успішний запуск нової ракети", NewsCategory.TECHNOLOGY, LocalDate.parse("2024-09-20")),
            new News(10L, "Робототехніка", "Новий робот-помічник на ринку", NewsCategory.TECHNOLOGY, LocalDate.parse("2024-09-15")),

            // Здоров'я - мала кількість
            new News(11L, "Здоров'я", "Нові поради від лікарів", NewsCategory.HEALTH, LocalDate.parse("2024-10-12")),
            new News(12L, "Фізичне здоров'я", "Важливість регулярного спорту", NewsCategory.HEALTH, LocalDate.parse("2024-09-28")),

            // Розваги - середня кількість
            new News(13L, "Кіно", "Новий фільм у прокаті", NewsCategory.ENTERTAINMENT, LocalDate.parse("2024-10-05")),
            new News(14L, "Музика", "Грандіозний концерт у Києві", NewsCategory.ENTERTAINMENT, LocalDate.parse("2024-09-30")),
            new News(15L, "Серіали", "Огляд популярних серіалів", NewsCategory.ENTERTAINMENT, LocalDate.parse("2024-09-20")),
            new News(16L, "Фільми", "Огляд популярних фільмів", NewsCategory.ENTERTAINMENT, LocalDate.parse("2024-09-20")),

            // Бізнес - багато новин
            new News(17L, "Бізнес", "Новий стартап залучив інвестиції", NewsCategory.BUSINESS, LocalDate.parse("2024-10-15")),
            new News(18L, "Фінанси", "Курс валют зріс", NewsCategory.BUSINESS, LocalDate.parse("2024-10-12")),
            new News(19L, "Ринки", "Зростання фондового ринку", NewsCategory.BUSINESS, LocalDate.parse("2024-10-10")),
            new News(20L, "Інновації", "Бізнес переходить на зелені технології", NewsCategory.BUSINESS, LocalDate.parse("2024-10-01")),
            new News(21L, "Аналіз", "Огляд економічних трендів", NewsCategory.BUSINESS, LocalDate.parse("2024-09-25"))

    ));


    private long nextId = 22L;

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
        categoryCountMap.putAll(newsList.stream()
                .collect(Collectors.groupingBy(
                        News::getCategory,
                        Collectors.summingInt(news -> 1)
                )));

        return categoryCountMap;
    }



    @Override
    public void createNews(News news) {
        news.setId(nextId);
        nextId++;
        newsList.add(news);
    }


    // Новий метод для оновлення новини
    @Override
    public Optional<News> updateNews(Long id, News updatedNews) {
        Optional<News> existingNews = newsList.stream()
                .filter(news -> news.getId().equals(id))
                .findFirst();

        if (existingNews.isPresent()) {
            News news = existingNews.get();
            news.setTitle(updatedNews.getTitle());
            news.setContent(updatedNews.getContent());
            news.setCategory(updatedNews.getCategory());
            news.setDate(updatedNews.getDate());
            return Optional.of(news);
        }
        return Optional.empty();
    }

    // Новий метод для видалення новини за ID
    @Override
    public boolean deleteNewsById(Long id) {
        return newsList.removeIf(news -> news.getId().equals(id));
    }

}