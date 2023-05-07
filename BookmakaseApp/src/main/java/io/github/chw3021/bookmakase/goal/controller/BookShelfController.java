package io.github.chw3021.bookmakase.goal.controller;

import io.github.chw3021.bookmakase.goal.domain.BookShelf;
import io.github.chw3021.bookmakase.goal.service.BookShelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bookshelves")
public class BookShelfController {

    private final BookShelfService bookShelfService;

    @Autowired
    public BookShelfController(BookShelfService bookShelfService) {
        this.bookShelfService = bookShelfService;
    }

    @PostMapping
    public ResponseEntity<BookShelf> createBookShelf() {
        BookShelf bookShelf = bookShelfService.createBookShelf();
        return ResponseEntity.created(URI.create("/bookshelves/" + bookShelf.getId())).body(bookShelf);
    }

    @GetMapping
    public ResponseEntity<List<BookShelf>> getAllBookShelves() {
    	List<BookShelf> bookShelves = bookShelfService.getAllBookShelves();
    	return ResponseEntity.ok(bookShelves);
	}
    @GetMapping("/{id}")
    public ResponseEntity<BookShelf> getBookShelfById(@PathVariable Long id) {
        BookShelf bookShelf = bookShelfService.getBookShelfById(id);
        return ResponseEntity.ok(bookShelf);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookShelf> updateBookShelf(@PathVariable Long id, @RequestBody BookShelf bookShelf) {
        BookShelf updatedBookShelf = bookShelfService.updateBookShelf(id, bookShelf);
        return ResponseEntity.ok(updatedBookShelf);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookShelf(@PathVariable Long id) {
        bookShelfService.deleteBookShelf(id);
        return ResponseEntity.noContent().build();
    }
}