package com.example.lab2.controllers;

import com.example.lab2.models.News;
import com.example.lab2.models.NewsCategory;
import com.example.lab2.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.commons.text.StringEscapeUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final NewsService newsService;
    private static final String NEWS_LIST = "newsList";
    private static final String NEWS_TABLE = "newsTableAdmin";

    @GetMapping("/create_news")
    public String getCreateNewsForm(Model model) {
        model.addAttribute("categories", NewsCategory.values());
        LocalDate currentDate  = LocalDate.now();
        model.addAttribute("currentDate", currentDate.toString());
        model.addAttribute("countOfCategoryNews",newsService.getNewsCountByCategory());
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
