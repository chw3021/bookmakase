package io.github.chw3021.bookmakase.goal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.chw3021.bookmakase.goal.domain.BookProgress;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookProgressRepository extends JpaRepository<BookProgress, Long>{

    List<BookProgress> findAllByMemberId(Long memberId);
}
