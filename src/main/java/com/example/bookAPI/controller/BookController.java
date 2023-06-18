package com.example.bookAPI.controller;

import com.example.bookAPI.domain.Book;
import com.example.bookAPI.dto.book.BookSaveRequestDto;
import com.example.bookAPI.dto.book.BookSearchResponseDto;
import com.example.bookAPI.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping("/{title}")
    public List<BookSearchResponseDto> getBooks(@PathVariable String title){
        return bookService.getBooks(title);
    }

    @PostMapping("/save")
    public void saveBooks(@RequestBody List<BookSaveRequestDto> books) {
        bookService.saveBooks(books);
    }
}