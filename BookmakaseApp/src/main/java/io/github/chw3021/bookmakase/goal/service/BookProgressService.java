package io.github.chw3021.bookmakase.goal.service;

import io.github.chw3021.bookmakase.goal.domain.BookProgress;
import io.github.chw3021.bookmakase.goal.repository.BookProgressRepository;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookProgressService{

    @Autowired
    private final BookProgressRepository bookProgressRepository;

    @Autowired
    private final MemberRepository memberRepository;

    public Double getProgress(Long memberId, Long bookId){
        BookProgress bookProgress = bookProgressRepository.findAllByMemberId(memberId).stream().filter(bp -> bp.getBook().getId()==bookId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + bookId));
        return bookProgress.getCurrentPage()*1.0/bookProgress.getTotalPage();
    }

    public void setCurrentPage(Long memberId, Long bookId, int page){
        BookProgress bookProgress = bookProgressRepository.findAllByMemberId(memberId).stream().filter(bp -> bp.getBook().getId()==bookId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + bookId));
        bookProgress.setCurrentPage(page);
        bookProgressRepository.save(bookProgress);
    }

    public BookProgress save(BookProgress bookProgress) {
        return bookProgressRepository.save(bookProgress);
    }

    public void delete(BookProgress bookProgress) {
        bookProgressRepository.delete(bookProgress);
    }

    public BookProgress update(BookProgress bookProgress) {
        return bookProgressRepository.save(bookProgress);
    }

}
