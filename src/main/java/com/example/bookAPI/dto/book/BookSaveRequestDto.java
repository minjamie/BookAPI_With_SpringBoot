package com.example.bookAPI.dto.book;

import lombok.Data;

import javax.persistence.Column;


@Data
public class BookSaveRequestDto {
    private String title;
    private String subTitle;
    private String writer;
    private String publisher;
    private String publishDate;
    private String img;
    private String detailNum;
    private String introduce;
    private boolean isEbook;
    private String category;
}
