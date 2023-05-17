package io.github.chw3021.bookmakase.journal.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.repository.BookRepository;
import io.github.chw3021.bookmakase.bookdata.service.BookService;
import io.github.chw3021.bookmakase.interparkapi.service.InterparkApiService;
import io.github.chw3021.bookmakase.journal.domain.Journal;
import io.github.chw3021.bookmakase.journal.dto.CreateJournalRequest;
import io.github.chw3021.bookmakase.journal.dto.UpdateJournalRequest;
import io.github.chw3021.bookmakase.journal.repository.JournalRepository;
import io.github.chw3021.bookmakase.review.repository.CommentRepository;
import io.github.chw3021.bookmakase.review.repository.ReportRepository;
import io.github.chw3021.bookmakase.review.repository.ReviewRepository;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import io.github.chw3021.bookmakase.signservice.service.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JournalService {

    @Autowired
    private final JournalRepository journalRepository;
	
    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final ReportRepository reportRepository;
    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final MemberService memberService;
    @Autowired
    private final BookService bookService;
    @Autowired
    private final InterparkApiService interparkApiService;
    
    public Journal createJournal(CreateJournalRequest request) {
        if(!bookService.isBookExist(request.getItemId())){
            bookService.saveBook(interparkApiService.getBookSearchResultsByItemId(request.getItemId()).getItem().get(0));
        }
    	Book book = bookRepository.findById(request.getItemId()).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + request.getItemId()));
        Member member = memberRepository.findById(request.getMemberId()).orElseThrow(() -> new IllegalArgumentException("Invalid member id: " + request.getMemberId()));
        
        Journal journal = Journal.builder()
        		.book(book)
        		.member(member)
        		.date(LocalDateTime.now())
        		.content(request.getContent())
        		.image(request.getImage())
        		.build();
        return journalRepository.save(journal);
    }

    public Journal getJournalById(Long id) {
        return journalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
    }

    public Journal updateJournal(Long id, UpdateJournalRequest request) {
        Journal journal = journalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
        journal.setDate(LocalDateTime.now());
        journal.setContent(request.getContent());
        return journalRepository.save(journal);
    }

    public boolean deleteJournal(Long id) {
        try {
            journalRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Journal> getAllJournals() {
        return journalRepository.findAll();
    }

    public List<Journal> getJournalsByMember(Member member) {
        return journalRepository.findByMemberOrderByDateDesc(member);
    }

    public List<Journal> getJournalsByBook(Book book) {
        return journalRepository.findByBookOrderByDateDesc(book);
    }

    public List<Journal> getJournalsByDate(LocalDate date) {
        return journalRepository.findByDateOrderByDateDesc(date);
    }
}