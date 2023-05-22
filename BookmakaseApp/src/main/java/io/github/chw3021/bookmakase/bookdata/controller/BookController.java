package io.github.chw3021.bookmakase.bookdata.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.service.BookRecommendService;
import io.github.chw3021.bookmakase.bookdata.service.BookService;

import java.util.HashSet;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    private final BookRecommendService bookRecommendService;

    @GetMapping("/recommend")
    public ResponseEntity<HashSet<Book>> recommend(@RequestParam Long memberId) {
        HashSet<Book> savedBook = bookRecommendService.recommend(memberId);
        return ResponseEntity.ok(savedBook);
    }

    @PostMapping("/save")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(savedBook);
    }
}