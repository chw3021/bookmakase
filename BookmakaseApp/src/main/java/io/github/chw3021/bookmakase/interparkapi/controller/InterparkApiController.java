package io.github.chw3021.bookmakase.interparkapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.chw3021.bookmakase.bookdata.domain.BookDto;
import io.github.chw3021.bookmakase.interparkapi.service.InterparkApiService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class InterparkApiController {
    private final InterparkApiService bookService;

    @GetMapping("/popular")
    public BookDto popular(@RequestParam int categoryId) {
        return bookService.getPopluarBooks(categoryId);
    }
    @GetMapping("/recommended")
    public BookDto recommended(@RequestParam int categoryId) {
        return bookService.getRecommendedBooks(categoryId);
    }
    @GetMapping("/newbooks")
    public BookDto newbooks(@RequestParam int categoryId) {
        return bookService.getNewBooks(categoryId);
    }
    @GetMapping("/search")
    public BookDto search(@RequestParam String query) {
        return bookService.getBookSearchResults(query);
    }
}