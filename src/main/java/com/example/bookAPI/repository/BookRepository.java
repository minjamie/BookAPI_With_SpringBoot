package com.example.bookAPI.repository;

import com.example.bookAPI.domain.Book;
import com.example.bookAPI.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    Optional<Book> findByTitle(String title);

}
