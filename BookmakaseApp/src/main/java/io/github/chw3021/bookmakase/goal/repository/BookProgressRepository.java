package io.github.chw3021.bookmakase.goal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.chw3021.bookmakase.goal.domain.BookProgress;

public interface BookProgressRepository extends JpaRepository<BookProgress, Long>{

}
