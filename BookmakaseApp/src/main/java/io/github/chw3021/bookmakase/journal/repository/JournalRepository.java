package io.github.chw3021.bookmakase.journal.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.journal.domain.Journal;

public interface JournalRepository extends JpaRepository<Journal, Long> {

    List<Journal> findByMemberIdOrderByDateDesc(Long memberId);
    List<Journal> findByBookOrderByDateDesc(Book book);
    List<Journal> findByDateOrderByDateDesc(LocalDate startDate);
}