package io.github.chw3021.bookmakase.bookdata.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.service.BookService;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/save")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(savedBook);
    }
}