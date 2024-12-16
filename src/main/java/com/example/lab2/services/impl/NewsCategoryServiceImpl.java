package com.example.lab2.services.impl;

import com.example.lab2.dto.category.NewsCategoryDto;
import com.example.lab2.models.NewsCategory;
import com.example.lab2.repositories.impl.NewsCategoryRepository;
import com.example.lab2.services.NewsCategoryNotFoundException;
import com.example.lab2.services.NewsCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewsCategoryServiceImpl implements NewsCategoryService {

    private final NewsCategoryRepository newsCategoryRepository;

    public NewsCategoryServiceImpl(NewsCategoryRepository newsCategoryRepository) {
        this.newsCategoryRepository = newsCategoryRepository;
    }

    @Override
    public NewsCategory createCategory(NewsCategoryDto newsCategoryDto) {
        NewsCategory newsCategory = NewsCategory.builder()
                .displayName(newsCategoryDto.getCategoryName())
                .build();
        return newsCategoryRepository.save(newsCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NewsCategory> getAllCategories() {
        return newsCategoryRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public NewsCategory getCategoryById(int id) {
        return newsCategoryRepository.findById((long) id)
                .orElseThrow(() -> new NewsCategoryNotFoundException((long) id));

    }

    @Override
    public NewsCategory updateCategory(int id, NewsCategoryDto newsCategoryDto) {
        NewsCategory existingCategory = newsCategoryRepository.findById((long) id)
                .orElseThrow(NewsCategoryNotFoundException::new);

        existingCategory.setDisplayName(newsCategoryDto.getCategoryName());

        return newsCategoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(int id) {
        if (!newsCategoryRepository.existsById((long) id)) {
            throw new NewsCategoryNotFoundException((long) id);
        }
        newsCategoryRepository.deleteById((long) id);
    }

    @Override
    @Transactional(readOnly = true)
    public NewsCategory getCategoryByName(String name) {
        return newsCategoryRepository.findByDisplayName(name).orElseThrow(NewsCategoryNotFoundException::new);
    }
}

