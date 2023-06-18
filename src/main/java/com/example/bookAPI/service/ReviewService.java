package com.example.bookAPI.service;

import com.example.bookAPI.domain.BookReview;
import com.example.bookAPI.repository.BookReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final BookReviewRepository bookReviewRepository;

    @Transactional
    public BookReview saveReview(BookReview bookReview) {
        BookReview saveReview = bookReviewRepository.save(bookReview);
        return saveReview;
    }
}
