package io.github.chw3021.bookmakase.goal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.chw3021.bookmakase.goal.domain.LikedBook;

@Repository
public interface LikedBookRepository extends JpaRepository<LikedBook,Long>{
    List<LikedBook> findAllByMemberId(Long memberId);
}
