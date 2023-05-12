package io.github.chw3021.bookmakase.bookdata.service;

import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import io.github.chw3021.bookmakase.signservice.domain.Member;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
	
	@Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final MemberRepository memberRepository;

    public boolean isBookExist(Long itemId){
        return bookRepository.findById(itemId).isPresent();
    }

    public void addLikedMemberId(Long itemId, Long memberId){
        Book book = bookRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Book not found with itemId: " + itemId));
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("Invalid member id: " + memberId));

        book.addlikedByMembers(member);
        bookRepository.save(book);
    }

    public List<Long> getLikedMemberIds(Long itemId) {
        Book book = bookRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Book not found with itemId: " + itemId));
        List<Member> likedMembers = book.getLikedByMembers();
        return likedMembers.stream()
                .map(Member::getId)
                .collect(Collectors.toList());
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}