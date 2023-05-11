package io.github.chw3021.bookmakase.goal.service;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.repository.BookRepository;
import io.github.chw3021.bookmakase.goal.domain.BookProgress;
import io.github.chw3021.bookmakase.goal.domain.BookShelf;
import io.github.chw3021.bookmakase.goal.dto.BookShelfDto;
import io.github.chw3021.bookmakase.goal.repository.BookProgressRepository;
import io.github.chw3021.bookmakase.goal.repository.BookShelfRepository;
import io.github.chw3021.bookmakase.signservice.domain.Member;
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
    @Autowired
    private final BookProgressRepository bookProgressRepository;

    private final BookProgressService bookProgressService;

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
        return optionalBookShelf.orElse(null);
    }

    public BookShelf updateCurrentReading(Long memberId, Long bookId, Integer page) {
        BookShelf bookShelf = getBookShelfByMemberId(memberId);
        BookProgress finding = bookShelf.getCurrentlyReading().stream().filter(f -> f.getBook().getId() == bookId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + bookId));

        final int index = bookShelf.getCurrentlyReading().indexOf(finding);
        List<BookProgress> current = bookShelf.getCurrentlyReading();

        finding.setCurrentPage(page);
        if(page>finding.getTotalPage()){
            finding.setCurrentPage(finding.getTotalPage());
        }
        bookProgressRepository.save(finding);

        current.set(index, finding);
        bookShelf.setCurrentlyReading(current);
        bookShelfRepository.save(bookShelf);

        return bookShelf;
    }
    
    public BookShelf addBookToShelf(Long memberId, Long bookId, Integer param) {
        BookShelf bookShelf = getBookShelfByMemberId(memberId);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + bookId));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member id: " + memberId));

        if(param==0) {
            bookShelf.addWantToRead(book);
        }
        else {
            bookShelf.addFinished(book);
        }
        bookShelfRepository.save(bookShelf);

        return bookShelf;
    }

    public BookShelf addBookProgressToShelf(Long memberId, Long bookId, Integer totalPage) {
        BookShelf bookShelf = getBookShelfByMemberId(memberId);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + bookId));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member id: " + memberId));

        BookProgress bookProgress = BookProgress.builder()
                .bookShelf(bookShelf)
                .book(book)
                .currentPage(0)
                .totalPage(totalPage)
                .member(member)
                .build();
        bookProgressRepository.save(bookProgress);
        bookShelf.addCurrentlyReading(bookProgress);

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
            BookProgress bookProgress = bookProgressRepository.findAllByMemberId(memberId).stream().filter(bp -> bp.getBook().getId()==bookId).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + bookId));
            bookShelf.removeCurrentlyReading(bookProgress);
            bookProgressRepository.delete(bookProgress);
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
