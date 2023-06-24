package com.example.bookAPI.repository;

import com.example.bookAPI.domain.Book;
import com.example.bookAPI.dto.book.BookCountPerCategoryResponseDto;
import com.example.bookAPI.dto.book.BookSearchResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByTitle(String title);

    @Query(value = "SELECT new com.example.bookAPI.dto.book.BookSearchResponseDto(b.id, b.title, b.subtitle, b.writer, b.publisher, b.publishDate, b.img, b.isEbook, b.count," +
            "coalesce(avg(br.rating),0))" +
            "FROM Book b " +
            "LEFT JOIN b.bookReviews br " +
            "WHERE b.title LIKE %:title% OR b.subtitle LIKE %:title% " +
            "GROUP BY b.id",
            countQuery = "SELECT COUNT(DISTINCT b.id) " +
                    "FROM Book b " +
                    "LEFT JOIN b.bookReviews br " +
                    "WHERE b.title LIKE %:title% OR b.subtitle LIKE %:title%")
    Page<BookSearchResponseDto> findByTitleContaining(@Param("title") String title, Pageable pageable);

    @Query(value = "SELECT new com.example.bookAPI.dto.book.BookSearchResponseDto(b.id, b.title, b.subtitle, b.writer, b.publisher, b.publishDate, b.img, b.isEbook, b.count," +
            "coalesce(avg(br.rating),0))" +
            "FROM Book b " +
            "LEFT JOIN b.bookReviews br " +
            "GROUP BY b.id",
            countQuery = "SELECT COUNT(DISTINCT b.id) " +
                    "FROM Book b " +
                    "LEFT JOIN b.bookReviews br ")
    Page<BookSearchResponseDto> findTopNBooksOrderByCreateDateTimeDesc(Pageable pageable);

    @Query(value = "SELECT new com.example.bookAPI.dto.book.BookSearchResponseDto(b.id, b.title, b.subtitle, b.writer, b.publisher, b.publishDate, b.img, b.isEbook, b.count," +
            "coalesce(avg(br.rating),0))" +
            "FROM Book b " +
            "LEFT JOIN b.bookReviews br " +
            "GROUP BY b.id",
            countQuery = "SELECT COUNT(DISTINCT b.id) " +
                    "FROM Book b " +
                    "LEFT JOIN b.bookReviews br")
    Page<BookSearchResponseDto> findByCategory(Integer categoryId, PageRequest pageable);

    @Query(value = "SELECT c.name, COUNT(b.book_id) AS count " +
            "FROM book AS b " +
            "LEFT JOIN category AS detail ON detail.id = b.category_id " +
            "LEFT JOIN category AS sub ON sub.id = detail.parent_category_id " +
            "LEFT JOIN category AS c ON c.id = sub.parent_category_id " +
            "OR c.name = sub.name " +
            "OR c.name =detail.name " +
            "WHERE c.parent_category_id is null " +
            "GROUP BY c.name", nativeQuery = true)
    List<BookCountPerCategoryResponseDto> countByCategory();
}
