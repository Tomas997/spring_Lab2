package com.example.lab2.controllers;

import com.example.lab2.models.News;
import com.example.lab2.repositories.NewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private NewsRepository newsRepository;
    @GetMapping("/news")
    public String getAllNews(Model model) {
        List<News> newsList = newsRepository.getAllNews(); // Отримати всі новини
        model.addAttribute("newsList", newsList); // Передати новини в модель
        return "newsTable"; // Назва Thymeleaf шаблону
    }
}
