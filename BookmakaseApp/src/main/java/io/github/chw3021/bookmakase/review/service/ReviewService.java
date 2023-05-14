package io.github.chw3021.bookmakase.review.service;

import io.github.chw3021.bookmakase.review.domain.Comment;
import io.github.chw3021.bookmakase.review.domain.Report;
import io.github.chw3021.bookmakase.review.domain.Review;
import io.github.chw3021.bookmakase.review.domain.dto.CommentDto;
import io.github.chw3021.bookmakase.review.domain.dto.ReportDto;
import io.github.chw3021.bookmakase.review.domain.dto.ReviewDto;
import io.github.chw3021.bookmakase.review.repository.CommentRepository;
import io.github.chw3021.bookmakase.review.repository.ReportRepository;
import io.github.chw3021.bookmakase.review.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
	
    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final ReportRepository reportRepository;
    @Autowired
    private final CommentRepository commentRepository;

    
    public Review save(ReviewDto reviewDto) {
        Review review = Review.builder()
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .book(reviewDto.getBook())
                .member(reviewDto.getMember())
                .build();
        return reviewRepository.save(review);
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }
    

    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + id));
    }

    @Transactional
    public Review update(Long id, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + id));
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());
        review.setBook(reviewDto.getBook());
        review.setMember(reviewDto.getMember());
        review.setComments(reviewDto.getComments());
        review.setLikes(reviewDto.getLikes());
        return reviewRepository.save(review);
    }

    @Transactional
    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
    
    public List<Report> findAllByReviewId(Long reviewId) {
        return reportRepository.findAllByReviewId(reviewId);
    }
    public List<Comment> getCommentsByReviewId(Long reviewId) {
        return commentRepository.findByReviewId(reviewId);
    }
    
    public void reportReview(Long reviewId, ReportDto reportDto) {
    	Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + reviewId));;
        if (review == null) {
            throw new EntityNotFoundException("Review with id " + reviewId + " not found");
        }
        Report report = new Report();
        report.setReview(review);
        report.setReason(reportDto.getReason());
        reportRepository.save(report);
    }

    public List<Report> getReviewReports(Long reviewId) {
        Review review = findById(reviewId);
        return review.getReports();
    }

    public Report processReport(Long reportId, ReportDto reportDto) {
    	Optional<Report> optionalReport = reportRepository.findById(reportId);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            report.setProcessed(true);
            report.setProcessedResult(reportDto.getProcessedResult());
            reportRepository.save(report);
            return report;
        } else {
            throw new IllegalStateException("Report with id " + reportId + " does not exist.");
        }
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
    

    public Comment addCommentToReview(Long reviewId, CommentDto commentDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + reviewId));

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .member(commentDto.getMember())
                .review(review)
                .build();

        // Review 객체에 Comment 객체 추가
        review.getComments().add(comment);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsOfReview(Long reviewId) {
        Review review = getReviewById(reviewId);
        return review.getComments();
    }

    public void addLikeToReview(Long reviewId) {
        Review review = getReviewById(reviewId);
        review.setLikes(review.getLikes() + 1);
        reviewRepository.save(review);
    }

    public void removeLikeFromReview(Long reviewId) {
        Review review = getReviewById(reviewId);
        review.setLikes(Math.max(0, review.getLikes() - 1));
        reviewRepository.save(review);
    }

    private Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + reviewId));
    }

}
