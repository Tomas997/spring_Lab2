package com.example.lab2.controllers;

import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import com.example.lab2.repositories.NewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/news/category")
    public String getNewsByCategory(@RequestParam("category") NewsCategory category, Model model) {
        Optional<List<News>> newsListOpt = newsRepository.getAllNewsByCategory(category);
        if (newsListOpt.isPresent()) {
            model.addAttribute("newsList", newsListOpt.get());
        } else {
            model.addAttribute("newsList", List.of());
        }
        return "newsTable";
    }

    @GetMapping("/news/search")
    public String searchNews(@RequestParam("keyword") String keyword, Model model) {
        Optional<List<News>> newsListOpt = newsRepository.getAllNewsByWord(keyword);
        if (newsListOpt.isPresent()) {
            model.addAttribute("newsList", newsListOpt.get());
        } else {
            model.addAttribute("newsList", List.of());
        }
        return "newsTable";
    }
}
