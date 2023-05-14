package io.github.chw3021.bookmakase.goal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.chw3021.bookmakase.goal.domain.ReadingBook;

@Repository
public interface ReadingBookRepository extends JpaRepository<ReadingBook,Long>{
    List<ReadingBook> findAllByMemberId(Long memberId);
}
