package com.example.bookStore.Services;

import com.example.bookStore.Models.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> listCategory();
    Category createCategory(Category category);
    void deleteCategory(Integer categoryId);
}
