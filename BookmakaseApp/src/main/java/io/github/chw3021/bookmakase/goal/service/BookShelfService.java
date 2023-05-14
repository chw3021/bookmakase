package io.github.chw3021.bookmakase.goal.service;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.repository.BookRepository;
import io.github.chw3021.bookmakase.bookdata.service.BookService;
import io.github.chw3021.bookmakase.goal.domain.BookProgress;
import io.github.chw3021.bookmakase.goal.domain.FinishedBook;
import io.github.chw3021.bookmakase.goal.domain.LikedBook;
import io.github.chw3021.bookmakase.goal.domain.ReadingBook;
import io.github.chw3021.bookmakase.goal.repository.BookProgressRepository;
import io.github.chw3021.bookmakase.goal.repository.FinishedBookRepository;
import io.github.chw3021.bookmakase.goal.repository.LikedBookRepository;
import io.github.chw3021.bookmakase.goal.repository.ReadingBookRepository;
import io.github.chw3021.bookmakase.interparkapi.service.InterparkApiService;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookShelfService {

    @Autowired
    private final FinishedBookRepository finishedBookRepository;
    @Autowired
    private final ReadingBookRepository readingBookRepository;
    @Autowired
    private final LikedBookRepository likedBookRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final GoalService goalService;
    @Autowired
    private final BookService bookService;
    @Autowired
    private final InterparkApiService interparkApiService;



    public List<LikedBook> getLikedBooksByMemberId(Long memberId) throws Exception {
    	return likedBookRepository.findAllByMemberId(memberId);
    }
    public List<ReadingBook> getReadingBooksByMemberId(Long memberId) throws Exception {
    	return readingBookRepository.findAllByMemberId(memberId);
    }
    public List<FinishedBook> getFinishedBooksByMemberId(Long memberId) throws Exception {
    	return finishedBookRepository.findAllByMemberId(memberId);
    }


    public ReadingBook updateCurrentReading(Long memberId, Long bookId, Integer page) throws Exception {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + bookId));
    	ReadingBook reading = getReadingBooksByMemberId(memberId).stream().filter(b -> b.getBook()==book).findFirst().get();

    	reading.setCurrentPage(page);
        if(page>reading.getTotalPage()){
        	reading.setCurrentPage(reading.getTotalPage());
        }

        readingBookRepository.save(reading);

        return reading;
    }
    
    public ResponseEntity<LikedBook> addLikeBook(Long memberId, Long bookId) throws Exception {
    	try {
            if(!bookService.isBookExist(bookId)){
                bookService.saveBook(interparkApiService.getBookSearchResultsByItemId(bookId).getItem().get(0));
            }
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + bookId));

            if(getLikedBooksByMemberId(memberId).stream().anyMatch(b -> b.getBook()==book)) {
                return ResponseEntity.badRequest().body(null);
            }
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member id: " + memberId));
            LikedBook likedBook = LikedBook.builder()
                    .member(member)
                    .book(book)
                    .build();
            
            likedBookRepository.save(likedBook);
            

            return ResponseEntity.ok(likedBook);
        }
    	catch(Exception e) {
            throw e;
        }
    }
    	


    //finished 추가시 goal 한권 완료추가
    public ResponseEntity<FinishedBook> addFinished(Long memberId, Long bookId) throws Exception {
    	try {
            if(!bookService.isBookExist(bookId)){
                bookService.saveBook(interparkApiService.getBookSearchResultsByItemId(bookId).getItem().get(0));
            }
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + bookId));

            if(getFinishedBooksByMemberId(memberId).stream().anyMatch(b -> b.getBook()==book)) {
                return ResponseEntity.badRequest().body(null);
            }
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member id: " + memberId));
            FinishedBook finishedBook = FinishedBook.builder()
                    .member(member)
                    .book(book)
                    .build();
            
            finishedBookRepository.save(finishedBook);

            if(getReadingBooksByMemberId(memberId).stream().anyMatch(b -> b.getBook() == book)){
            	removeBookFromShelf(memberId, bookId, 1);
            }
            goalService.setAllMemberGoalReadedByCategoryId(memberId, book.getCategoryId(),1);
            
            return ResponseEntity.ok(finishedBook);
        }
    	catch(Exception e) {
            throw e;
        }
    }

    public ResponseEntity<ReadingBook> addReadingBook(Long memberId, Long bookId, Integer totalPage) throws Exception {
    	try {
            if(!bookService.isBookExist(bookId)){
                bookService.saveBook(interparkApiService.getBookSearchResultsByItemId(bookId).getItem().get(0));
            }
            Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + bookId));
            //bookService.addLikedMemberId(bookId, memberId);

            if(getReadingBooksByMemberId(memberId).stream().anyMatch(b -> b.getBook() ==book)) {
                return ResponseEntity.badRequest().body(null);
            }
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member id: " + memberId));
            
            ReadingBook rnb = ReadingBook.builder()
                    .member(member)
                    .book(book)
                    .currentPage(0)
                    .totalPage(totalPage)
                    .build();
            
            readingBookRepository.save(rnb);

            if(getFinishedBooksByMemberId(memberId).stream().anyMatch(b -> b.getBook() == book)){
            	removeBookFromShelf(memberId, bookId, 2);
            }

            return ResponseEntity.ok(rnb);
        }
    	catch(Exception e) {
            throw e;
        }
    }
    public boolean removeBookFromShelf(Long memberId, Long bookId, Integer param) {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + bookId));
    	try {
	        if(param==0) {
	        	LikedBook lb = getLikedBooksByMemberId(memberId).stream().filter(b -> b.getBook()==book).findFirst().get();
	        	likedBookRepository.delete(lb);
	        	
	        }
	        else if(param==1) {
	        	ReadingBook rb = getReadingBooksByMemberId(memberId).stream().filter(b -> b.getBook()==book).findFirst().get();
	        	readingBookRepository.delete(rb);
	        }
	        else {
	        	FinishedBook fb = getFinishedBooksByMemberId(memberId).stream().filter(b -> b.getBook()==book).findFirst().get();
	        	finishedBookRepository.delete(fb);
	        }
	
	        return true;
	    }
		catch(Exception e) {
			e.printStackTrace();
	        return false;
	    }
    }

    public boolean deleteBookShelfByMemberId(Long memberId, Integer param) throws Exception {

    	try {
	        if(param==0) {
	        	List<LikedBook> r = getLikedBooksByMemberId(memberId);
	        	likedBookRepository.deleteAll(r);
	        }
	        else if(param==1) {
	        	List<ReadingBook> r = getReadingBooksByMemberId(memberId);
	        	readingBookRepository.deleteAll(r);
	        }
	        else {
	        	List<FinishedBook> r = getFinishedBooksByMemberId(memberId);
	        	finishedBookRepository.deleteAll(r);
	        }
	
	        return true;
	    }
		catch(Exception e) {
	        return false;
	    }
    }

}
