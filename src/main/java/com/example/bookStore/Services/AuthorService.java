package com.example.bookStore.Services;

import com.example.bookStore.Models.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {
    Author createAuthor(Author author);
    List<Author> listAuthor();
    Author getAuthor(Integer id);
    void deleteAuthor(Integer authorId);
    Author updateAuthor(Author author,Integer id);
}
