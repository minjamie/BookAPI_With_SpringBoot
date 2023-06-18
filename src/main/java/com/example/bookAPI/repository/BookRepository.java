package com.example.bookAPI.repository;

import com.example.bookAPI.domain.Book;
import com.example.bookAPI.domain.Member;
import com.example.bookAPI.dto.book.BookSearchResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByTitle(String title);

    @Query("SELECT new com.example.bookAPI.dto.book.BookSearchResponseDto(b.id, b.title, b.subtitle, b.writer, " +
            "IFNULL(avg(br.rating), 0)g) " +
            "FROM Book b " +
            "LEFT JOIN b.bookReviews br " +
            "WHERE b.title LIKE %:title% OR b.subtitle LIKE %:title% " +
            "GROUP BY b.id")
    List<BookSearchResponseDto> findByTitleContaining(@Param("title") String title);
}
