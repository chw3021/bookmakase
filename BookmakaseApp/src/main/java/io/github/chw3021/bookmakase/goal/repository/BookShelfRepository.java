package io.github.chw3021.bookmakase.goal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.chw3021.bookmakase.goal.domain.BookShelf;

public interface BookShelfRepository extends JpaRepository<BookShelf, Long>{

    Optional<BookShelf> findByMemberId(Long memberId);
}
