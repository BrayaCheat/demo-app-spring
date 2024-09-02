package com.example.bookStore.Repositories;

import com.example.bookStore.Models.Book;
import com.example.bookStore.Models.DTO.Response.BookDuplicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query(value = "select * from tb_book where author_id = :id", nativeQuery = true)
    List<Book> getBookByAuthorId(@Param("id") Integer authorId);

    Optional<Book> findByTitle(String title);

    @Query(value = "select count(*) from tb_book", nativeQuery = true)
    Long countBookLength();

    @Query(value = "select b from tb_book where b.title like %:title% and b.author.name like %:author%", nativeQuery = true)
    List<Book> searchBook(@Param("title") String title, @Param("author") String author);

    @Query(value = "SELECT b.book_id, b.title, sub.count AS duplicate_count FROM tb_book b JOIN (SELECT title, COUNT(*) AS count FROM tb_book GROUP BY title HAVING COUNT(*) > 1) sub ON b.title = sub.title", nativeQuery = true)
    Page<BookDuplicate> findDuplicateBook(Pageable pageable);
}
