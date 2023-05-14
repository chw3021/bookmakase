package io.github.chw3021.bookmakase.goal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.chw3021.bookmakase.goal.domain.FinishedBook;

@Repository
public interface FinishedBookRepository extends JpaRepository<FinishedBook,Long>{
    List<FinishedBook> findAllByMemberId(Long memberId);
}
