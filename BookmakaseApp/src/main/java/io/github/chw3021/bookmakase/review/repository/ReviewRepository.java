package io.github.chw3021.bookmakase.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.chw3021.bookmakase.review.domain.Review;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long>{
    List<Review> findAllByMemberId(Long memberId);
}
