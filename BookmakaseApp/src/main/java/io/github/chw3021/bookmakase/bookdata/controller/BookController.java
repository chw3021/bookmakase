package io.github.chw3021.bookmakase.bookdata.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.chw3021.bookmakase.bookdata.dto.InterparkResponseDto;
import io.github.chw3021.bookmakase.bookdata.service.BookService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/popular")
    public InterparkResponseDto popular() {
        return bookService.getPopluarBooks();
    }
}