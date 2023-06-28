package com.example.bookAPI.controller;

import com.example.bookAPI.dto.book.BookCountPerCategoryResponseDto;
import com.example.bookAPI.dto.book.BookSaveRequestDto;
import com.example.bookAPI.dto.book.BookSearchResponseDto;
import com.example.bookAPI.dto.book.BookSearchResult;
import com.example.bookAPI.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "BookApiController", description = "책 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "책 이름으로 검색", description = "책 이름으로 페이지네이션 반환")
    @GetMapping("/search")
    public BookSearchResult getBooksByTitle(
            @Parameter(description = "제목", required = true, example = "sql") @RequestParam(value = "title", defaultValue = "sql") String title,
            @Parameter(description = "조회 페이지", required = true, example = "0")  @RequestParam(value = "page", defaultValue = "1") int page,
            @Parameter(description = "조회 사이즈", required = true, example = "sql")  @RequestParam(value = "size", defaultValue = "10") int size
    ){
        PageRequest pageable = PageRequest.of(page-1, size);
        Page<BookSearchResponseDto> resultPage = bookService.getBooksByTitle(title, pageable);
        return new BookSearchResult(resultPage.getContent(), resultPage.getTotalPages(), resultPage.getTotalElements(), resultPage.getNumber()+1, resultPage.isLast());
    }

    @Operation(summary = "가장 최근에 구매한 책", description = "최근 구매한 책 Top N 반환")
    @GetMapping("/purchased")
    public BookSearchResult getPurchasedBooks(
            @Parameter(description = "상위 조회 갯수", example = "10", required = false)  @RequestParam(value = "topSize", required = false) Integer topSize,
            @Parameter(description = "조회 페이지", example = "0")  @RequestParam(value = "page", defaultValue = "1" , required = false) int page,
            @Parameter(description = "조회 사이즈", example = "10")  @RequestParam(value = "size", defaultValue = "10" , required = false) int size
    ){
        PageRequest pageable;
        if (topSize!=null) {
            pageable = PageRequest.of(0, topSize, Sort.by(Sort.Direction.DESC, "createDateTime"));
        } else {
            pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "createDateTime"));
        }

        Page<BookSearchResponseDto> resultPage = bookService.getPurchasedBooks(pageable);
        return new BookSearchResult(resultPage.getContent(), resultPage.getTotalPages(), resultPage.getTotalElements(), resultPage.getNumber()+1, resultPage.isLast());
    }

    @Operation(summary = "카테고리 별 책 리스트 조회", description = "최 상위 카테고리로 책 리스트 반환")
    @GetMapping("/category/{categoryId}")
    public BookSearchResult getBooksByCategory(
            @PathVariable(name = "categoryId", required = true) int categoryId,
            @Parameter(description = "서브 카테고리 명", required = false) @RequestParam(value = "subCategory", required = false) String subCategory,
            @Parameter(description = "제목", required = false) @RequestParam(value = "title", required = false) String title,
            @Parameter(description = "조회 페이지", required = true, example = "0")  @RequestParam(value = "page", defaultValue = "1") int page,
            @Parameter(description = "조회 사이즈", required = true, example = "10")  @RequestParam(value = "size", defaultValue = "10") int size
    ){
        PageRequest pageable = PageRequest.of(page-1, size , Sort.by(Sort.Direction.DESC, "createDateTime"));
        Page<BookSearchResponseDto> resultPage = bookService.getBooksByCategory(categoryId, title, subCategory, pageable);
        return new BookSearchResult(resultPage.getContent(), resultPage.getTotalPages(), resultPage.getTotalElements(), resultPage.getNumber()+1, resultPage.isLast());
    }

    @Operation(summary = "카테고리 별 책 갯수 조회", description = "카테고리에 해당하는 책 전체 갯수 리스트 반환")
    @GetMapping("/category/count")
    public List<BookCountPerCategoryResponseDto> getBookCountPerCategory(){
        return bookService.getBookCountPerCategory();
    }

    @Operation(summary = "서브 카테고리 별 책 갯수 조회", description = "서브 카테고리에 해당하는 책 전체 갯수 리스트 반환")
    @GetMapping("/category/{categoryId}/count")
    public List<BookCountPerCategoryResponseDto> getBookCountPerSubCategory(
            @PathVariable(name = "categoryId", required = true) int categoryId
            ){
        return bookService.getBookCountPerSubCategory(categoryId);
    }

    @Operation(summary = "책 리스트 저장", description = "스크래핑으로 받은 책 리스트 저장")
    @PostMapping("/save")
    public void saveBooks(@RequestBody List<BookSaveRequestDto> books) {
        bookService.saveBooks(books);
    }

}