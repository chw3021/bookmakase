package io.github.chw3021.bookmakase.interparkapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.chw3021.bookmakase.bookdata.dto.BookDto;
import io.github.chw3021.bookmakase.interparkapi.service.InterparkApiService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class InterparkApiController {
    private final InterparkApiService bookService;

    @GetMapping("/popular")
    public BookDto popular(int categoryId) {
        return bookService.getPopluarBooks(categoryId);
    }
    @GetMapping("/recommended")
    public BookDto recommended(int categoryId) {
        return bookService.getRecommendedBooks(categoryId);
    }
    @GetMapping("/newbooks")
    public BookDto newbooks(int categoryId) {
        return bookService.getNewBooks(categoryId);
    }
    @GetMapping("/search")
    public BookDto search(String query) {
        return bookService.getBookSearchResults(query);
    }
}