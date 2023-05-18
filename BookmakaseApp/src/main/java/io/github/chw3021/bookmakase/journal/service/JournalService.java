package io.github.chw3021.bookmakase.journal.service;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.repository.BookRepository;
import io.github.chw3021.bookmakase.bookdata.service.BookService;
import io.github.chw3021.bookmakase.interparkapi.service.InterparkApiService;
import io.github.chw3021.bookmakase.journal.domain.Journal;
import io.github.chw3021.bookmakase.journal.dto.CreateJournalRequest;
import io.github.chw3021.bookmakase.journal.dto.UpdateJournalRequest;
import io.github.chw3021.bookmakase.journal.repository.JournalRepository;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class JournalService {

    @Autowired
    private final JournalRepository journalRepository;

    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final MemberRepository memberRepository;
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
                .title(request.getTitle())
        		.date(LocalDate.now())
        		.content(request.getContent())
        		.image(request.getImage())
        		.build();
        return journalRepository.save(journal);
    }

    public List<Journal> search(Long memberId, String param) {
        List<Journal> all = getJournalsByMember(memberId);
        List<Journal> sort = all.stream().filter(r -> {
            Boolean title = r.getTitle().contains(param);
            Boolean con = r.getContent().contains(param);
            Boolean bt = r.getBook().getTitle().contains(param);
            Boolean ba = r.getBook().getAuthor().contains(param);
            return title||con||bt||ba;
        }).toList();
        return sort;
    }


    public Journal getJournalById(Long id) {
        return journalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
    }

    public Journal updateJournal(UpdateJournalRequest request) {
        Journal journal = journalRepository.findById(request.getId()).orElseThrow(() -> new IllegalArgumentException("Invalid id: " + request.getId()));
        journal.setDate(LocalDate.now());
        journal.setTitle(request.getTitle());
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

    public void deleteMemberJournal(Long memberId) {
        getJournalsByMember(memberId).forEach(j ->{
            journalRepository.delete(j);
        });

    }
    public List<Journal> getAllJournals() {
        return journalRepository.findAll();
    }

    public List<Journal> getJournalsByMember(Long memberId) {
        return journalRepository.findAllByMemberId(memberId);
    }

    public List<Journal> getJournalsByDate(LocalDate date) {
        return journalRepository.findAllByDate(date);
    }
}