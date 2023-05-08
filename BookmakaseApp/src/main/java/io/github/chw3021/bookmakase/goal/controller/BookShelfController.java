package io.github.chw3021.bookmakase.goal.controller;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.goal.domain.BookProgress;
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
    @GetMapping("/getBook/{id}")
    public ResponseEntity<BookShelf> getBookShelfById(@PathVariable Long id) {
        BookShelf bookShelf = bookShelfService.getBookShelfById(id);
        return ResponseEntity.ok(bookShelf);
    }
    @PostMapping("/{id}/wantToRead")
    public ResponseEntity<BookShelf> addWantToRead(@PathVariable Long id, @RequestBody Book book) {
        BookShelf bookShelf = bookShelfService.getBookShelfById(id);
        bookShelf.addWantToRead(book);
        bookShelfService.updateBookShelf(id, bookShelf);
        return ResponseEntity.ok(bookShelf);
    }
    @PostMapping("/{id}/NowRead")
    public ResponseEntity<BookShelf> addCurrentlyReading(@PathVariable Long id, @RequestBody BookProgress book) {
        BookShelf bookShelf = bookShelfService.getBookShelfById(id);
        bookShelf.addCurrentlyReading(book);
        bookShelfService.updateBookShelf(id, bookShelf);
        return ResponseEntity.ok(bookShelf);
    }
    @PostMapping("/{id}/finished")
    public ResponseEntity<BookShelf> addFinished(@PathVariable Long id, @RequestBody Book book) {
        BookShelf bookShelf = bookShelfService.getBookShelfById(id);
        bookShelf.addFinished(book);
        bookShelfService.updateBookShelf(id, bookShelf);
        return ResponseEntity.ok(bookShelf);
    }
    
    @PutMapping("/updateBookshelf/{id}")
    public ResponseEntity<BookShelf> updateBookShelf(@PathVariable Long id, @RequestBody BookShelf bookShelf) {
        BookShelf updatedBookShelf = bookShelfService.updateBookShelf(id, bookShelf);
        return ResponseEntity.ok(updatedBookShelf);
    }

    @DeleteMapping("/deleteBookshelf/{id}")
    public ResponseEntity<Void> deleteBookShelf(@PathVariable Long id) {
        bookShelfService.deleteBookShelf(id);
        return ResponseEntity.noContent().build();
    }
}