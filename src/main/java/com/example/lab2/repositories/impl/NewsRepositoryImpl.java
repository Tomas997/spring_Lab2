package com.example.lab2.repositories.impl;

import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepositoryImpl extends JpaRepository<News, Long> {

    @Query("SELECT n FROM News n WHERE n.title LIKE %:keywords% OR n.content LIKE %:keywords%")
    List<News> findNewsByKeywords(String keywords);

    @Query("SELECT n FROM News n WHERE n.category.displayName = :categoryName")
    List<News> findNewsByCategoryName(String categoryName);

    List<News> findByCategory(NewsCategory category);
}