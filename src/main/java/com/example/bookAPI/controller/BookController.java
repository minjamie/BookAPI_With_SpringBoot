package com.example.bookAPI.controller;

import com.example.bookAPI.domain.Book;
import com.example.bookAPI.dto.book.BookSaveRequestDto;
import com.example.bookAPI.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    @PostMapping("/save")
    public void saveBooks(@RequestBody List<BookSaveRequestDto> books) {
        bookService.saveBooks(books);
    }
}