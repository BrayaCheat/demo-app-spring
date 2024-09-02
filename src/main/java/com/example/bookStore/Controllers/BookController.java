package com.example.bookStore.Controllers;

import com.example.bookStore.Models.Book;
import com.example.bookStore.Models.DTO.Response.ApiResponse;
import com.example.bookStore.Models.DTO.Response.BookDuplicate;
import com.example.bookStore.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> createBook(@Validated @RequestBody Book book) {
        try {
            Book res = bookService.createBook(book);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>("Book created", res, HttpStatus.CREATED, LocalDateTime.now()));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(ex.getMessage(), null, HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<Book>>> listBook(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "2") int size, @RequestParam(defaultValue = "title") String sortBy, @RequestParam(defaultValue = "asc") String sortDirection) {
        Page<Book> res = bookService.listBook(page, size, sortBy, sortDirection);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Book list", res, HttpStatus.OK, LocalDateTime.now()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Book>> getBook(Integer id) {
        Book res = bookService.getBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Book get", res, HttpStatus.OK, LocalDateTime.now()));
    }

    @GetMapping("/getBookByAuthorId/{authorId}")
    public ResponseEntity<ApiResponse<List<Book>>> getBookByAuthorId(@PathVariable Integer authorId) {
        try {
            List<Book> res = bookService.getBookByAuthorId(authorId);
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>("Book get by author id", res, HttpStatus.OK, LocalDateTime.now()));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(ex.getMessage(), null, HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }

    }

    @GetMapping("/getBookLength")
    public ResponseEntity<ApiResponse<Long>> countBookLength() {
        Long res = bookService.countBookLength();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                "Total books",
                                res,
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Book>>> searchBook(@RequestParam String title, @RequestParam String author) {
        List<Book> res = bookService.searchBook(title, author);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                "Result of searching",
                                res,
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<ApiResponse<String>> deleteBook(@Validated @PathVariable Integer bookId) {
        try {
            bookService.deleteBook(bookId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            new ApiResponse<>(
                                    "Book with id " + bookId + " deleted",
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

    @GetMapping("/duplicateBook")
    public ResponseEntity<ApiResponse<Page<BookDuplicate>>> duplicateBook(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                "Duplicate book page" + page,
                                bookService.duplicateBook(size, page, sortBy, sortDirection),
                                HttpStatus.OK,
                                LocalDateTime.now()

                        )
                );
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<ApiResponse<Book>> updateBook(@PathVariable Integer bookId, @Validated @RequestBody Book book) {
        try {
            Book res = bookService.updateBook(bookId, book);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            new ApiResponse<>(
                                    "Book updated",
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

    @PatchMapping("/updateTitle/{bookId}")
    public ResponseEntity<ApiResponse<Book>> updateTitle(@PathVariable Integer bookId, @RequestParam String title) {
        try {
            Book res = bookService.updateTitle(bookId, title);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            new ApiResponse<>(
                                    "Book's title updated",
                                    res,
                                    HttpStatus.OK,
                                    LocalDateTime.now()
                            )
                    );
        } catch (IllegalArgumentException ex) {
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
