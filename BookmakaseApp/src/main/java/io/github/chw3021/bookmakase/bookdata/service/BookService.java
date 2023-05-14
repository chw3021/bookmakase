package io.github.chw3021.bookmakase.bookdata.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.repository.BookRepository;
import io.github.chw3021.bookmakase.goal.domain.LikedBook;
import io.github.chw3021.bookmakase.goal.repository.LikedBookRepository;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
	
	@Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final LikedBookRepository likedBookRepository;

    public boolean isBookExist(Long itemId){
        return bookRepository.findById(itemId).isPresent();
    }

/*
    public void addLikedMemberId(Long itemId, Long memberId) throws Exception{
        Book book = bookRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Book not found with itemId: " + itemId));
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("Invalid member id: " + memberId));
    	if(!likedBookRepository.findByMemberId(memberId).isPresent()) {
    		LikedBookShelf likedBook= LikedBookShelf.builder()
    				.member(member)
    				.wantToRead(new ArrayList<Book>())
                    .build();
        	likedBook.addWantToRead(book);
        	likedBookRepository.save(likedBook);
        	return;
    	}
    	LikedBookShelf likedBook= likedBookRepository.findByMemberId(memberId).orElseThrow(() -> new RuntimeException("Invalid member id: " + memberId));

    	likedBook.addWantToRead(book);
    	
    	likedBookRepository.flush();

    }
    public List<Long> getLikedMemberIds(Long itemId) {
        Book book = bookRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Book not found with itemId: " + itemId));
        List<Member> likedMembers = book.getLikedByMembers();
        return likedMembers.stream()
                .map(Member::getId)
                .collect(Collectors.toList());
    }
*/
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}