package io.github.chw3021.bookmakase.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.chw3021.bookmakase.review.domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

	List<Report> findAllByReviewId(Long reviewId);
}