package io.github.chw3021.bookmakase.review.repository;

import io.github.chw3021.bookmakase.review.domain.Heart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeartRepository extends JpaRepository<Heart,Long>{

	List<Heart> findAllByReviewId(Long reviewId);

	List<Heart> findAllByMemberId(Long reviewId);
}
