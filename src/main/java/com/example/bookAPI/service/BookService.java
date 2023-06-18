package com.example.bookAPI.service;

import com.example.bookAPI.domain.Book;
import com.example.bookAPI.domain.Category;
import com.example.bookAPI.dto.book.BookSaveRequestDto;
import com.example.bookAPI.dto.book.BookSearchResponseDto;
import com.example.bookAPI.repository.BookRepository;
import com.example.bookAPI.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    @Transactional
    public void saveBooks(List<BookSaveRequestDto> bookSaveRequestDtos) {
        for (BookSaveRequestDto bookRequestDto : bookSaveRequestDtos) {
            Book book = createBook(bookRequestDto);

            Optional<Book> existedBook = bookRepository.findByTitle(bookRequestDto.getTitle());
            if (existedBook.isPresent()) {
                Book existBook = existedBook.get();
                existBook.setCount(existBook.getCount() + 1);
            } else {
                bookRepository.save(book);
            }
        }
    }

    private Book createBook(BookSaveRequestDto bookSaveRequestDto) {
        List<String> categoryName = List.of(bookSaveRequestDto.getCategory().split(","));
        Category category = getCategory(categoryName);
        Book book = new Book();
        book.setTitle(bookSaveRequestDto.getTitle());
        book.setSubtitle(bookSaveRequestDto.getSubTitle());
        book.setImg(bookSaveRequestDto.getImg());
        book.setWriter(bookSaveRequestDto.getWriter());
        book.setPublisher(bookSaveRequestDto.getPublisher());
        book.setPublishDate(bookSaveRequestDto.getPublishDate());
        book.setDetailNum(bookSaveRequestDto.getDetailNum());
        book.setEbook(bookSaveRequestDto.isEbook());
        book.setIntroduce(bookSaveRequestDto.getIntroduce());
        book.setCount(1);
        book.setCategory(category);
        return book;
    }

    private Category getCategory(List<String> categoryNames) {
        Category parentCategory = null;
        for (String categoryName : categoryNames) {
            Category category = categoryRepository.findByName(categoryName);

            if(category == null){
                category = new Category(categoryName);
            }
            if (parentCategory != null) {
                category.setParentCategory(parentCategory);
            }
            categoryRepository.save(category);
            parentCategory = category;
        }
        return parentCategory;
    }

    @Transactional(readOnly = true)
    public List<BookSearchResponseDto> getBooks
            (String title) {
        return bookRepository.findByTitleContaining(title);
    }

    @Transactional(readOnly = true)
    public Optional<Book> getBook(Long bookId) {
        return bookRepository.findById(bookId);
    }
}