package com.example.bookStore.Services.ServiceImpl;

import com.example.bookStore.Exceptions.AuthorNotFoundException;
import com.example.bookStore.Models.Author;
import com.example.bookStore.Models.Book;
import com.example.bookStore.Models.Category;
import com.example.bookStore.Models.DTO.Response.BookDuplicate;
import com.example.bookStore.Repositories.AuthorRepository;
import com.example.bookStore.Repositories.BookRepository;
import com.example.bookStore.Repositories.CategoryRepository;
import com.example.bookStore.Services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Book createBook(Book book) {
        try {
            Optional<Book> existingBook = bookRepository.findByTitle(book.getTitle());
            if (existingBook.isPresent()) {
                throw new RuntimeException("Book with title: " + book.getTitle() + " is already exist!");
            }
            Author findAuthor = authorRepository.findById(book.getAuthor().getAuthorId()).orElseThrow(() -> new AuthorNotFoundException("Author not found"));
            Set<Category> categories = new HashSet<>();
            for (Category category : book.getCategories()) {
                Category findCategory = categoryRepository.findById(category.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found with id: " + category.getCategoryId()));
                categories.add(findCategory);
            }
            book.setCategories(categories);
            book.setAuthor(findAuthor);
            return bookRepository.save(book);
        } catch (AuthorNotFoundException ex) {
            throw new AuthorNotFoundException(ex.getMessage());
        }

    }

    @Override
    public Page<Book> listBook(int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return bookRepository.findAll(pageable);
    }

    @Override
    public Page<BookDuplicate> duplicateBook(int size, int page, String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return bookRepository.findDuplicateBook(pageable);
    }

    @Override
    public Book getBook(Integer id) {
        return bookRepository.findById(id).orElseThrow(() -> new AuthorNotFoundException("Book not found with id: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getBookByAuthorId(Integer authorId) {
        List<Book> books = bookRepository.getBookByAuthorId(authorId);
        if (books.isEmpty()) {
            throw new RuntimeException("No books found for author with ID: " + authorId);
        }
        return books;
    }

    @Override
    public Long countBookLength() {
        return bookRepository.countBookLength();
    }

    @Override
    public List<Book> searchBook(String title, String author) {
        if (title.isEmpty() || author.isEmpty()) {
            throw new RuntimeException("Search must includes: Book's title or Author's name");
        }
        return bookRepository.searchBook(title, author);
    }

    @Override
    public void deleteBook(Integer bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new RuntimeException("Book not found with id: " + bookId);
        }
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        for(Category category : book.getCategories()){
            category.getBooks().remove(book);
            categoryRepository.save(category);
        }
        bookRepository.deleteById(bookId);
    }

    @Override
    public Book updateBook(Integer bookId, Book book) {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        existingBook.setTitle(book.getTitle());
        existingBook.setPublishedDate(book.getPublishedDate());
        return bookRepository.save(existingBook);
    }

    @Override
    public Book updateTitle(Integer bookId, String title) {
        Book existingBook = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        existingBook.setTitle(title);
        existingBook.setUpdatedAt(LocalDateTime.now());
        return bookRepository.save(existingBook);
    }


}
