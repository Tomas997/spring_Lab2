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

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final NewsService newsService;

    @GetMapping("/create_news")
    public String getCreateNewsForm(Model model) {
        model.addAttribute("categories", NewsCategory.values());
        LocalDate currentDate  = LocalDate.now();
        model.addAttribute("currentDate", currentDate.toString());
        model.addAttribute("countOfCategoryNews",newsService.getNewsCountByCategory());
        model.addAttribute("newsCategoryStatus", newsService.getNewsCategoryStatus());
        return "newsForm";
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
}
