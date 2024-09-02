package com.example.bookStore.Services.ServiceImpl;

import com.example.bookStore.Models.Category;
import com.example.bookStore.Repositories.CategoryRepository;
import com.example.bookStore.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> listCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(Category category) {
        try {
            Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
            if(existingCategory.isPresent()){
                throw new RuntimeException("Category is already exist!");
            }
            return categoryRepository.save(category);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        if(!categoryRepository.existsById(categoryId)){
            throw new RuntimeException("Category with id: " + categoryId + " not found");
        }
        categoryRepository.deleteById(categoryId);
    }
}
