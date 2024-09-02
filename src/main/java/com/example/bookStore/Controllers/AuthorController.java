package com.example.bookStore.Controllers;

import com.example.bookStore.Models.Author;
import com.example.bookStore.Models.DTO.Response.ApiResponse;
import com.example.bookStore.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Author>> createAuthor(@Validated @RequestBody Author author) {
        try {
            Author res = authorService.createAuthor(author);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            new ApiResponse<>(
                                    "Author created",
                                    res,
                                    HttpStatus.CREATED,
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

    @GetMapping
    public ResponseEntity<ApiResponse<List<Author>>> listAuthor() {
        List<Author> res = authorService.listAuthor();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                "Author list",
                                res,
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Author>> getAuthor(@PathVariable Integer id) {
        Author res = authorService.getAuthor(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                "Author get",
                                res,
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<ApiResponse<String>> deleteAuthor(@Validated @PathVariable Integer authorId) {
        try {
            authorService.deleteAuthor(authorId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            new ApiResponse<>(
                                    "Author with id " + authorId + " deleted",
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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Author>> updateAuthor(@Validated @RequestBody Author author, @PathVariable Integer id) {
        Author res = authorService.updateAuthor(author, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                "Author updated",
                                res,
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }
}
