package io.github.chw3021.bookmakase.journal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping
    public ResponseEntity<Journal> createJournal(@RequestBody CreateJournalRequest request) {
        Journal journal = journalService.createJournal(request);
        return ResponseEntity.ok(journal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable Long id) {
        Journal journal = journalService.getJournalById(id);
        if (journal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(journal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Journal> updateJournal(@PathVariable Long id, @RequestBody UpdateJournalRequest request) {
        Journal updatedJournal = journalService.updateJournal(id, request);
        if (updatedJournal == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedJournal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJournal(@PathVariable Long id) {
        boolean deleted = journalService.deleteJournal(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Journal>> getAllJournals() {
        List<Journal> journals = journalService.getAllJournals();
        return ResponseEntity.ok(journals);
    }
}