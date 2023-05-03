package io.github.chw3021.bookmakase.journal.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.journal.domain.Journal;
import io.github.chw3021.bookmakase.journal.dto.CreateJournalRequest;
import io.github.chw3021.bookmakase.journal.dto.UpdateJournalRequest;
import io.github.chw3021.bookmakase.journal.repository.JournalRepository;
import io.github.chw3021.bookmakase.signservice.domain.Member;

@Service
public class JournalService {

    @Autowired
    private final JournalRepository journalRepository;

    public JournalService(JournalRepository journalRepository) {
        this.journalRepository = journalRepository;
    }

    public Journal createJournal(CreateJournalRequest request) {
        Journal journal = new Journal();
        journal.setBook(request.getBook());
        journal.setMember(request.getMember());
        journal.setDate(request.getDate());
        journal.setContent(request.getContent());
        return journalRepository.save(journal);
    }

    public Journal getJournalById(Long id) {
        return journalRepository.findById(id).orElse(null);
    }

    public Journal updateJournal(Long id, UpdateJournalRequest request) {
        Journal journal = journalRepository.findById(id).orElse(null);
        if (journal == null) {
            return null;
        }
        journal.setDate(request.getDate());
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