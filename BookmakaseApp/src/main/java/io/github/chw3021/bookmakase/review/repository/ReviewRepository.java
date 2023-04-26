package io.github.chw3021.bookmakase.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.chw3021.bookmakase.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long>{

}
