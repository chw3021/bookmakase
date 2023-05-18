package io.github.chw3021.bookmakase.journal.repository;

import io.github.chw3021.bookmakase.journal.domain.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {

    List<Journal> findAllByMemberId(Long memberId);
    List<Journal> findAllByDate(LocalDate startDate);

}