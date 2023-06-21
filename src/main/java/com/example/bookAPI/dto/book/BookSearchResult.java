package com.example.bookAPI.dto.book;
import lombok.Data;
import java.util.List;

@Data
public class BookSearchResult{

    private List<BookSearchResponseDto> books;
    private int totalPage;
    private long totalCount;
    private int currentPage;
    private boolean isLastPage;

    public BookSearchResult(List<BookSearchResponseDto> books, int totalPage, long totalCount, int currentPage, boolean isLastPage) {
        this.books = books;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.isLastPage = isLastPage;
    }
}
