package com.example.lab2.services.impl;


import com.example.lab2.dto.NewsDto;
import com.example.lab2.entity.News;
import com.example.lab2.entity.NewsCategory;
import com.example.lab2.repositories.impl.NewsRepositoryImpl;
import com.example.lab2.services.exeption.NewsNotFoundException;
import com.example.lab2.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepositoryImpl newsRepository;
    private final NewsCategoryServiceImpl newsCategoryServiceImpl;

    @Override
    public Optional<List<News>> getAllNewsByWord(String keywords) {
        return Optional.ofNullable(newsRepository.findNewsByCategoryName(keywords));
    }

    @Override
    public Optional<List<News>> getAllNewsByCategory(NewsCategory category) {
        return Optional.ofNullable(newsRepository.findNewsByCategoryName(category.getDisplayName()));
    }


    @Override
    public News createNews(NewsDto news) {
        NewsCategory category = newsCategoryServiceImpl.getCategoryById(news.getCategoryId());
        News newsEntity = News.builder()
                .title(news.getTitle())
                .date(news.getDate())
                .content(news.getContent())
                .category(category)
                .build();
        return newsRepository.save(newsEntity);
    }

    @Override
    public Optional<List<News>> getAllNews() {
        return Optional.of(newsRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }


    @Override
    @Transactional
    public News updateNews(Long id, NewsDto updatedNews) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));

        news.setTitle(updatedNews.getTitle());
        news.setContent(updatedNews.getContent());
        news.setCategory(newsCategoryServiceImpl.getCategoryById(updatedNews.getCategoryId()));

        return newsRepository.save(news);
    }


    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new NewsNotFoundException(id);
        }
        newsRepository.deleteById(id);
    }


    @Transactional
    public void deleteAllNewsByCategory(int categoryId) {
        List<News> newsToDelete = newsRepository.findByCategory(newsCategoryServiceImpl.getCategoryById(categoryId));
        if (newsToDelete.isEmpty()) {
            throw new NewsNotFoundException((long) categoryId);
        }
        newsRepository.deleteAll(newsToDelete);
    }
}


