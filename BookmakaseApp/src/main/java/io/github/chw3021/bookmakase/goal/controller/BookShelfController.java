package io.github.chw3021.bookmakase.goal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.github.chw3021.bookmakase.goal.domain.FinishedBook;
import io.github.chw3021.bookmakase.goal.domain.LikedBook;
import io.github.chw3021.bookmakase.goal.domain.ReadingBook;
import io.github.chw3021.bookmakase.goal.service.BookShelfService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/bookshelves")
@RequiredArgsConstructor
public class BookShelfController {

    @Autowired
    private final BookShelfService bookShelfService;


    @GetMapping(value = "/getLikedBooks")
    public ResponseEntity<List<LikedBook>> getLikedBooks(@RequestParam Long memberId) throws Exception {
        List<LikedBook> bookShelf = bookShelfService.getLikedBooksByMemberId(memberId);
        return ResponseEntity.ok(bookShelf);
    }

    @GetMapping(value = "/getReadingBooks")
    public ResponseEntity<List<ReadingBook>> getReadingBooks(@RequestParam Long memberId) throws Exception {
        List<ReadingBook> bookShelf = bookShelfService.getReadingBooksByMemberId(memberId);
        return ResponseEntity.ok(bookShelf);
    }

    @GetMapping(value = "/getFinishedBooks")
    public ResponseEntity<List<FinishedBook>> getFinishedBooks(@RequestParam Long memberId) throws Exception {
        List<FinishedBook> bookShelf = bookShelfService.getFinishedBooksByMemberId(memberId);
        return ResponseEntity.ok(bookShelf);
    }

    @PostMapping("/updateCurrentReading")
    public ResponseEntity<ReadingBook> updateCurrentReading(@RequestParam Long memberId, @RequestParam Long bookId, @RequestParam int page) throws Exception {
    	ReadingBook r = bookShelfService.updateCurrentReading(memberId, bookId, page);
        return ResponseEntity.ok(r);
    }
    

    @PutMapping("/addLike")
    public ResponseEntity<LikedBook> addLike(@RequestParam Long memberId, @RequestParam Long bookId) throws Exception {
    	return bookShelfService.addLikeBook(memberId, bookId);
    }
    
    //다읽은책으로 해당 도서를 추가시 해당 도서의 카테고리를 가진 목표의 읽은책이 하나 늘어난다.
    @PutMapping("/addFinished")
    public ResponseEntity<FinishedBook> addFinished(@RequestParam Long memberId, @RequestParam Long bookId) throws Exception {
    	return bookShelfService.addFinished(memberId, bookId);
    }

    @PutMapping("/addReading")
    public ResponseEntity<ReadingBook> addReading(@RequestParam Long memberId, @RequestParam Long bookId, @RequestParam int totalPage) throws Exception {
    	return bookShelfService.addReadingBook(memberId, bookId, totalPage);
    }

    //param -> 0=읽고싶은책, 1다읽은책, 2읽고있는책
    @DeleteMapping("/removeBook")
    public ResponseEntity<Boolean> removeBook(@RequestParam Long memberId, @RequestParam Long bookId, @RequestParam int param) throws Exception {
        return ResponseEntity.ok(bookShelfService.removeBookFromShelf(memberId, bookId, param));
    }
    @DeleteMapping("/deleteBookShelfByMemberId")
    public ResponseEntity<Boolean> deleteBookShelfByMemberId(@RequestParam Long memberId, @RequestParam int param) throws Exception {
    	return ResponseEntity.ok(bookShelfService.deleteBookShelfByMemberId(memberId, param));
    }
}