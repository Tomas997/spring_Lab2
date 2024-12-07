package com.example.lab2.controllers;

import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import com.example.lab2.services.NewsService;
import lombok.AllArgsConstructor;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private static final String NEWS_LIST = "newsList";
    private static final String NEWS_TABLE = "newsTableAdmin";
    private final NewsService newsService;

    @GetMapping("/create_news")
    public String getCreateNewsForm(Model model) {
        model.addAttribute("categories", NewsCategory.values());
        LocalDate currentDate = LocalDate.now();
        model.addAttribute("currentDate", currentDate.toString());
        model.addAttribute("countOfCategoryNews", newsService.getNewsCountByCategory());
        model.addAttribute("newsCategoryStatus", newsService.getNewsCategoryStatus());
        return "newsForm";
    }

    @GetMapping("/news_table_admin")
    public String getNewsTableAdmin(Model model) {
        Optional<List<News>> newsListOpt = newsService.getAllNews();
        model.addAttribute("newsList", newsListOpt.orElse(List.of()));
        return "newsTableAdmin";
    }

    @PostMapping("/create_news")
    public String createNews(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("category") NewsCategory category,
            @RequestParam("date") LocalDate date
    ) {
        String safeTitle = StringEscapeUtils.escapeHtml4(title);        //Фільтрація символів: скрипти видаляються з полів, наприклад, бібліотеку Apache Commons Lang
        String safeContent = StringEscapeUtils.escapeHtml4(content);

        News news = News.builder()
                .title(safeTitle)
                .content(safeContent)
                .category(category)
                .date(date)
                .build();

        newsService.createNews(news);
        return "redirect:/admin/create_news";
    }


    @GetMapping("/news")
    public String getAllNews(@RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "5") int size,
                             Model model) {
        Optional<List<News>> newsPage = newsService.getNewsWithPaginationAndFiltering(null, null, page, size);
        long totalNews = newsService.getAllNews().map(List::size).orElse(0); // Загальна кількість новин

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
