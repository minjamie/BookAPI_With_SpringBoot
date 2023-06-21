package com.example.bookAPI.dto.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchResponseDto {
    private Long bookId;
    private String title;
    private String subtitle;
    private String writer;
    private String publisher;
    private String publishDate;
    private String img;
    private boolean isEbook;
    private int count;
    private Double rating;

}
