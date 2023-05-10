package io.github.chw3021.bookmakase.goal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.chw3021.bookmakase.goal.domain.BookGoal;

@Repository
public interface BookGoalRepository  extends JpaRepository<BookGoal, Long> {
    List<BookGoal> findAllByMemberId(Long memberId);
}
