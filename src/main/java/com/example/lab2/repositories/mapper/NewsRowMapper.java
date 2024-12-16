package com.example.lab2.repositories.mapper;

import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewsRowMapper implements RowMapper<News> {
    @Override
    public News mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new News(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("content"),
                NewsCategory.valueOf(rs.getString("category")),
                rs.getDate("date").toLocalDate()
        );
    }
}
