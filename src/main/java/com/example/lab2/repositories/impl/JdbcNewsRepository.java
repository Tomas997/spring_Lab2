package com.example.lab2.repositories.impl;

import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import com.example.lab2.repositories.NewsRepository;
import com.example.lab2.repositories.mapper.NewsRowMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@Primary
public class JdbcNewsRepository implements NewsRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcNewsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<List<News>> getAllNews() {
        String sql = "SELECT * FROM news";
        List<News> newsList = jdbcTemplate.query(sql, new NewsRowMapper());
        return newsList.isEmpty() ? Optional.empty() : Optional.of(newsList);
    }

    @Override
    public Optional<List<News>> getAllNewsByCategory(NewsCategory category) {
        String sql = "SELECT * FROM news WHERE category = ?";
        List<News> newsList = jdbcTemplate.query(sql, new NewsRowMapper(), category.name());
        return newsList.isEmpty() ? Optional.empty() : Optional.of(newsList);
    }

    @Override
    public Optional<List<News>> getAllNewsByWord(String keywords) {
        String sql = "SELECT * FROM news WHERE LOWER(title) LIKE ? OR LOWER(content) LIKE ?";
        String searchPattern = "%" + keywords.toLowerCase() + "%";
        List<News> newsList = jdbcTemplate.query(sql, new NewsRowMapper(), searchPattern, searchPattern);
        return newsList.isEmpty() ? Optional.empty() : Optional.of(newsList);
    }

    @Override
    public Map<NewsCategory, Integer> getNewsCountByCategory() {
        String sql = "SELECT category, COUNT(*) AS count FROM news GROUP BY category";
        return jdbcTemplate.query(sql, rs -> {
            Map<NewsCategory, Integer> result = new HashMap<>();
            while (rs.next()) {
                result.put(NewsCategory.valueOf(rs.getString("category")), rs.getInt("count"));
            }
            return result;
        });
    }

    @Override
    public News createNews(News news) {
        String sql = "INSERT INTO news (title, content, category, date) VALUES (?, ?, ?, ?) RETURNING id";
        Long id = jdbcTemplate.queryForObject(sql, Long.class, news.getTitle(), news.getContent(), news.getCategory().name(), news.getDate());
        News newsResponse = News.builder()
                .id(id)
                .title(news.getTitle())
                .content(news.getContent())
                .date(news.getDate())
                .category(news.getCategory())
                .build();

        return newsResponse;
    }

    @Override
    public Optional<News> updateNews(Long id, News updatedNews) {
        String sql = "UPDATE news SET title = ?, content = ?, category = ?, date = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql,
                updatedNews.getTitle(),
                updatedNews.getContent(),
                updatedNews.getCategory().name(),
                updatedNews.getDate(),
                id
        );

        if (rowsAffected > 0) {
            News newsResponse = News.builder()
                    .id(id)
                    .title(updatedNews.getTitle())
                    .content(updatedNews.getContent())
                    .date(updatedNews.getDate())
                    .category(updatedNews.getCategory())
                    .build();
            return Optional.of(newsResponse);
        }

        return Optional.empty();
    }


    @Override
    public boolean deleteNewsById(Long id) {
        String sql = "DELETE FROM news WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }
}

