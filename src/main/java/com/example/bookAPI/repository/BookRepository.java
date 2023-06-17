package com.example.bookAPI.repository;

import com.example.bookAPI.domain.Book;
import com.example.bookAPI.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByTitle(String title);

    @Query("SELECT b FROM Book b LEFT OUTER JOIN b.bookReviews bR WHERE b.title LIKE %:title% OR b.subtitle LIKE %:title%")
    List<Book> findByTitleContaining(@Param("title") String title);
}
