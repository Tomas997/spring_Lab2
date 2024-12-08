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
    public String getAllNews(@RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "5") int size,
                             Model model) {
            Optional<List<News>> newsPage = newsService.getNewsWithPaginationAndFiltering(null, null, page, size);
            long totalNews = newsService.getAllNews().map(List::size).orElse(0);
            model.addAttribute("newsList", newsPage.orElse(List.of()));
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", (int) Math.ceil((double) totalNews / size));
        return NEWS_TABLE;
    }

    @GetMapping("/news/category")
    public String getNewsByCategory(@RequestParam(value = "page", defaultValue = "0") int page,
                                    @RequestParam(value = "size", defaultValue = "5") int size,
                                    @RequestParam("category") NewsCategory category, Model model) {
        Optional<List<News>> newsPage = newsService.getNewsWithPaginationAndFiltering(null, category, page, size);
        long totalNews = newsService.getNewsWithPaginationAndFiltering(null, category, 0, Integer.MAX_VALUE)
                .map(List::size)
                .orElse(0);
        model.addAttribute(NEWS_LIST, newsPage.orElse(List.of()));
        model.addAttribute("category", String.valueOf(category));
        model.addAttribute("totalPages", (int) Math.ceil((double) totalNews / size));
        model.addAttribute("currentPage", page);
        return NEWS_TABLE;
    }

    @GetMapping("/news/search")
    public String searchNews(@RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "5") int size,
                             @RequestParam("keyword") String keyword, Model model) {
        Optional<List<News>> newsPage = newsService.getNewsWithPaginationAndFiltering(keyword, null, page, size);
        long totalNews = newsService.getNewsWithPaginationAndFiltering(keyword, null, 0, Integer.MAX_VALUE)
                .map(List::size)
                .orElse(0);
        model.addAttribute(NEWS_LIST, newsPage.orElse(List.of()));
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPages", (int) Math.ceil((double) totalNews / size));
        model.addAttribute("currentPage", page);
        return NEWS_TABLE;
    }
}
