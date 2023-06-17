package com.example.bookAPI.repository;

import com.example.bookAPI.domain.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookReviewRepository extends JpaRepository<BookReview, Long> {

}
