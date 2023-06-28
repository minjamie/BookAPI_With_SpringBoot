package com.example.bookAPI.service;

import com.example.bookAPI.domain.Book;
import com.example.bookAPI.domain.Category;
import com.example.bookAPI.dto.Enum.CategoryType;
import com.example.bookAPI.dto.book.BookCountPerCategoryResponseDto;
import com.example.bookAPI.dto.book.BookSaveRequestDto;
import com.example.bookAPI.dto.book.BookSearchResponseDto;
import com.example.bookAPI.repository.BookRepository;
import com.example.bookAPI.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public Page<BookSearchResponseDto> getBooksByTitle
            (String title, Pageable pageable) {
        return bookRepository.findByTitleContaining(title, pageable);
    }

    @Transactional(readOnly = true)
    public Optional<Book> getBook(Long bookId) {
        return bookRepository.findById(bookId);
    }

    public Page<BookSearchResponseDto> getPurchasedBooks(Pageable pageable) {
        return bookRepository.findTopNBooksOrderByCreateDateTimeDesc(pageable);
    }

    public String getCategoryName(int id) {
        for (CategoryType ct : CategoryType.values()) {
            if (ct.getId() == id) {
                return ct.getName();
            }
        }
        return null;
    }

    public Page<BookSearchResponseDto> getBooksByCategory(int categoryId, String title, String subCategory, Pageable pageable) {
        String categoryName = getCategoryName(categoryId);
        return bookRepository.findByCategory(categoryName, title, subCategory, pageable);
    }

    public List<Book> getBooksByIds(List<Long> bookIds){
        return bookRepository.findAllById(bookIds);
    }

    public List<BookCountPerCategoryResponseDto> getBookCountPerCategory() {
        return bookRepository.countByCategory();
    }

    public List<BookCountPerCategoryResponseDto> getBookCountPerSubCategory(int categoryId) {
        String categoryName = getCategoryName(categoryId);
        return bookRepository.countBySubCategory(categoryName);
    }


}