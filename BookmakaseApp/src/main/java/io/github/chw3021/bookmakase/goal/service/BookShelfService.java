package io.github.chw3021.bookmakase.goal.service;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.repository.BookRepository;
import io.github.chw3021.bookmakase.goal.domain.BookProgress;
import io.github.chw3021.bookmakase.goal.domain.BookShelf;
import io.github.chw3021.bookmakase.goal.dto.BookShelfDto;
import io.github.chw3021.bookmakase.goal.repository.BookShelfRepository;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookShelfService {

    @Autowired
    private final BookShelfRepository bookShelfRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final MemberRepository memberRepository;


    public BookShelf createBookShelf(BookShelfDto bookshelfdto) throws Exception {
        try {
            BookShelf bookshelf = BookShelf.builder()
            		.id(bookshelfdto.getId())
            		.member(bookshelfdto.getMember(memberRepository))
            		.wantToRead(bookshelfdto.getWantToRead())
            		.currentlyReading(bookshelfdto.getCurrentlyReading())
            		.finished(bookshelfdto.getFinished())
            		.build();
    		return bookShelfRepository.save(bookshelf);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
    }

    public List<BookShelf> getAllBookShelves() {
        return bookShelfRepository.findAll();
    }



    public BookShelf getBookShelfByMemberId(Long memberId) {
        Optional<BookShelf> optionalBookShelf = bookShelfRepository.findByMemberId(memberId);
        if (optionalBookShelf.isPresent()) {
            return optionalBookShelf.get();
        }
		return null;
    }
    
    
    public BookShelf addBookToShelf(Long memberId, Long bookId, Integer param) {
        BookShelf bookShelf = getBookShelfByMemberId(memberId);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + bookId));
        
        if(param==0) {
        	bookShelf.addWantToRead(book);
        }
        else if(param==1) {
            BookProgress bookProgress = new BookProgress(book, bookShelf);
        	bookShelf.addCurrentlyReading(bookProgress);;
        }
        else {
        	bookShelf.addFinished(book);
        }
        bookShelfRepository.save(bookShelf);

        return bookShelf;
    }

    public BookShelf removeBookFromShelf(Long memberId, Long bookId, Integer param) {
        BookShelf bookShelf = getBookShelfByMemberId(memberId);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + bookId));
        
        if(param==0) {
        	bookShelf.removeWantToRead(book);        
        }
        else if(param==1) {
            BookProgress bookProgress = new BookProgress(book, bookShelf);
        	bookShelf.removeCurrentlyReading(bookProgress);
        }
        else {
        	bookShelf.removeFinished(book);
        }
        bookShelfRepository.save(bookShelf);

        return bookShelf;
    }

    public void deleteBookShelf(Long memberId) {
        BookShelf bookShelf = getBookShelfByMemberId(memberId);

        if (bookShelf != null) {
            bookShelfRepository.delete(bookShelf);
        } else {
            throw new IllegalArgumentException("Invalid memberId id: " + memberId);
        }
    }
}
