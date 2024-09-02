package com.example.bookStore.Services;

import com.example.bookStore.Models.Book;
import com.example.bookStore.Models.DTO.Response.BookDuplicate;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    Book createBook(Book book);
    Page<Book> listBook(int size, int page, String sortBy, String sortDirection);
    Book getBook(Integer id);
    List<Book> getBookByAuthorId(Integer authorId);
    Long countBookLength();
    List<Book> searchBook(String title, String author);
    void deleteBook(Integer bookId);
    Page<BookDuplicate> duplicateBook(int size, int page, String sortBy, String sortDirection);
    Book updateBook(Integer bookId, Book book);
    Book updateTitle(Integer bookId, String title);
}
