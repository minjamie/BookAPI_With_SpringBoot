package com.example.bookAPI.dto.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.sql.Date;
import java.time.LocalDateTime;


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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createDateTime;
}
