package io.github.chw3021.bookmakase.journal.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.chw3021.bookmakase.journal.domain.Journal;
import io.github.chw3021.bookmakase.journal.dto.CreateJournalRequest;
import io.github.chw3021.bookmakase.journal.dto.UpdateJournalRequest;
import io.github.chw3021.bookmakase.journal.service.JournalService;

@RestController
@RequestMapping("/journals")
public class JournalController {

    private final JournalService journalService;

    @Autowired
    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @PostMapping("/save")
    public ResponseEntity<Journal> createJournal(@RequestBody CreateJournalRequest request) {
        Journal journal = journalService.createJournal(request);
        return ResponseEntity.ok(journal);
    }

    @GetMapping("/findJournalsByMember")
    public ResponseEntity<List<Journal>> findUserJourners(@RequestParam Long memberId) {
        List<Journal> journal = journalService.getJournalsByMember(memberId);
        if (journal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(journal);
    }


    @GetMapping("/findJournalsByDate")
    public ResponseEntity<List<Journal>> getJournalsByDate(@RequestParam LocalDate localDate) {
        List<Journal> journal = journalService.getJournalsByDate(localDate);
        if (journal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(journal);
    }


    @GetMapping("/findById")
    public ResponseEntity<Journal> getJournalById(@RequestParam Long id) {
        Journal journal = journalService.getJournalById(id);
        if (journal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(journal);
    }

    @PutMapping("/update")
    public ResponseEntity<Journal> updateJournal(@RequestParam UpdateJournalRequest request) {
        Journal updatedJournal = journalService.updateJournal(request);
        if (updatedJournal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedJournal);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteJournal(@RequestParam Long id) {
        boolean deleted = journalService.deleteJournal(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Journal>> getAllJournals() {
        List<Journal> journals = journalService.getAllJournals();
        return ResponseEntity.ok(journals);
    }
}