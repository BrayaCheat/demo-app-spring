package com.example.bookStore.Controllers;

import com.example.bookStore.Models.Category;
import com.example.bookStore.Models.DTO.Response.ApiResponse;
import com.example.bookStore.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> listCategories() {
        List<Category> res = categoryService.listCategory();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                "Book listed",
                                res,
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> createCategory(@Validated @RequestBody Category category) {
        Category res = categoryService.createCategory(category);
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            new ApiResponse<>(
                                    "Book created",
                                    res,
                                    HttpStatus.OK,
                                    LocalDateTime.now()
                            )
                    );
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            new ApiResponse<>(
                                    ex.getMessage(),
                                    null,
                                    HttpStatus.BAD_REQUEST,
                                    LocalDateTime.now()
                            )
                    );
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            new ApiResponse<>(
                                    "Category deleted",
                                    null,
                                    HttpStatus.OK,
                                    LocalDateTime.now()
                            )
                    );
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            new ApiResponse<>(
                                    ex.getMessage(),
                                    null,
                                    HttpStatus.BAD_REQUEST,
                                    LocalDateTime.now()
                            )
                    );
        }
    }
}
