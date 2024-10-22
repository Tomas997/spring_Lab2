package com.example.lab2.controllers;

import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import com.example.lab2.repositories.NewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private NewsRepository newsRepository;
    @GetMapping("/create_news")
    public String getCreateNewsForm(Model model) {
        model.addAttribute("categories", NewsCategory.values()); // Pass categories to the model
        return "newsForm"; // Return the name of the Thymeleaf template without 'templates/'
    }
    @PostMapping("/create_news")
    public String createNews(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("category") NewsCategory category,
            @RequestParam("date") String date,
            Model model) {

        // Створюємо об'єкт новини
        News news = News.builder()
                .title(title)
                .content(content)
                .category(category)
                .date(LocalDate.parse(date))
                .build();

        // Додаємо новину в репозиторій
        newsRepository.addNews(news);

        // Перенаправляємо на сторінку зі списком новин
        return "redirect:/admin/create_news";
    }

}
