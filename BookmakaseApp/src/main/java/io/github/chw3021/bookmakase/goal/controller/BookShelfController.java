package io.github.chw3021.bookmakase.goal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.chw3021.bookmakase.goal.domain.BookShelf;
import io.github.chw3021.bookmakase.goal.dto.BookShelfDto;
import io.github.chw3021.bookmakase.goal.service.BookShelfService;

@RestController
@RequestMapping("/bookshelves")
public class BookShelfController {

    private final BookShelfService bookShelfService;

    @Autowired
    public BookShelfController(BookShelfService bookShelfService) {
        this.bookShelfService = bookShelfService;
    }

    @PostMapping
    public ResponseEntity<BookShelf> createBookShelf(@RequestParam BookShelfDto bookshelfdto) throws Exception {
        return new ResponseEntity<>(bookShelfService.createBookShelf(bookshelfdto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookShelf>> getAllBookShelves() {
    	List<BookShelf> bookShelves = bookShelfService.getAllBookShelves();
    	return ResponseEntity.ok(bookShelves);
	}
    @GetMapping("/getBookShelf")
    public ResponseEntity<BookShelf> getBookShelfById(@RequestParam Long memberId) {
        BookShelf bookShelf = bookShelfService.getBookShelfByMemberId(memberId);
        return ResponseEntity.ok(bookShelf);
    }

    @PutMapping("/updateCurrentReading")
    public ResponseEntity<BookShelf> updateCurrentReading(@RequestParam Long memberId, @RequestParam Long bookId, @RequestParam int page) {
        BookShelf bookShelf = bookShelfService.updateCurrentReading(memberId, bookId, page);
        return ResponseEntity.ok(bookShelf);
    }
    
    //param -> 0=읽고싶은책, 1다읽은책

    @PostMapping("/addBook")
    public ResponseEntity<BookShelf> addBook(@RequestParam Long memberId, @RequestParam Long bookId, @RequestParam int param) {
        BookShelf bookShelf = bookShelfService.addBookToShelf(memberId, bookId, param);
        return ResponseEntity.ok(bookShelf);
    }

    @PostMapping("/addBookProgress")
    public ResponseEntity<BookShelf> addBookProgress(@RequestParam Long memberId, @RequestParam Long bookId, @RequestParam int totalPage) {
        BookShelf bookShelf = bookShelfService.addBookProgressToShelf(memberId, bookId, totalPage);
        return ResponseEntity.ok(bookShelf);
    }

    //param -> 0=읽고싶은책, 1다읽은책, 2읽고있는책
    @DeleteMapping("/removeBook")
    public ResponseEntity<BookShelf> removeBook(@RequestParam Long memberId, @RequestParam Long bookId, @RequestParam int param) {
        BookShelf bookShelf = bookShelfService.removeBookFromShelf(memberId, bookId, param);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/deleteBookshelf")
    public ResponseEntity<Void> deleteBookShelf(@RequestParam Long memberId) {
        bookShelfService.deleteBookShelf(memberId);
        return ResponseEntity.noContent().build();
    }
}