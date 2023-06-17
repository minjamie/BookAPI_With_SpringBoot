package com.example.bookAPI.dto.book.review;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookReviewResponseDto {
    private Long memberId;
    private Long bookId;
    private String content;
    private BigDecimal rate;
}
