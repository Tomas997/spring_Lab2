package com.example.lab2.controllers;

import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import com.example.lab2.services.NewsService;
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
    private final NewsService newsService;
    private static final String NEWS_LIST = "newsList";
    private static final String NEWS_TABLE = "newsTable";

    @GetMapping("/news")
    public String getAllNews(Model model) {
        Optional<List<News>> newsListOpt = newsService.getAllNews();
        model.addAttribute(NEWS_LIST, newsListOpt.orElse(List.of()));
        return NEWS_TABLE;
    }

    @GetMapping("/news/category")
    public String getNewsByCategory(@RequestParam("category") NewsCategory category, Model model) {
        Optional<List<News>> newsListOpt = newsService.getAllNewsByCategory(category);
        model.addAttribute(NEWS_LIST, newsListOpt.orElse(List.of()));
        model.addAttribute("category", String.valueOf(category));
        return NEWS_TABLE;
    }

    @GetMapping("/news/search")
    public String searchNews(@RequestParam("keyword") String keyword, Model model) {
        Optional<List<News>> newsListOpt = newsService.getAllNewsByWord(keyword);
        model.addAttribute(NEWS_LIST, newsListOpt.orElse(List.of()));
        return NEWS_TABLE;
    }
}
