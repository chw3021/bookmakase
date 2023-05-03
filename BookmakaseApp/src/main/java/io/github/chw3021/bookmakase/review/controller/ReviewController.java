package io.github.chw3021.bookmakase.review.controller;

import io.github.chw3021.bookmakase.review.domain.Comment;
import io.github.chw3021.bookmakase.review.domain.Report;
import io.github.chw3021.bookmakase.review.domain.Review;
import io.github.chw3021.bookmakase.review.domain.dto.CommentDto;
import io.github.chw3021.bookmakase.review.domain.dto.ReportDto;
import io.github.chw3021.bookmakase.review.domain.dto.ReviewDto;
import io.github.chw3021.bookmakase.review.service.ReviewService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    

    @GetMapping
    public List<Review> findAll() {
        return reviewService.findAll();
    }

    @GetMapping("/{id}")
    public Review findById(@PathVariable("id") Long id) {
        return reviewService.findById(id);
    }

    @PostMapping
    public Review save(@RequestBody ReviewDto reviewDto) {
        return reviewService.save(reviewDto);
    }

    @PutMapping("/{id}")
    public Review update(@PathVariable("id") Long id, @RequestBody ReviewDto reviewDto) {
        return reviewService.update(id, reviewDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        reviewService.delete(id);
    }
    
    @PostMapping("/{id}/reports")
    public void reportReview(@PathVariable("id") Long postId, @RequestBody ReportDto reportDto) {
    	reviewService.reportReview(postId, reportDto);
    }

    @GetMapping("/{id}/reports")
    public List<Report> getReviewReports(@PathVariable("id") Long postId) {
        return reviewService.getReviewReports(postId);
    }

    @PutMapping("/reports/{reportId}/process")
    public Report processReport(@PathVariable("reportId") Long reportId, @RequestBody ReportDto reportDto) {
        return reviewService.processReport(reportId, reportDto);
    }

    @GetMapping("/reports")
    public List<Report> getAllReports() {
        return reviewService.getAllReports();
    }

    
    @PostMapping("/{reviewId}/comments/add")
    public void addCommentToReview(@PathVariable("reviewId") Long reviewId, @RequestBody CommentDto commentDto) {
        reviewService.addCommentToReview(reviewId, commentDto);
    }

    @GetMapping("/{reviewId}/comments")
    public List<Comment> getCommentsOfReview(@PathVariable("reviewId") Long reviewId) {
        return reviewService.getCommentsOfReview(reviewId);
    }

    @PostMapping("/{reviewId}/likes")
    public void addLikeToReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.addLikeToReview(reviewId);
    }

    @DeleteMapping("/{reviewId}/likes")
    public void removeLikeFromReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.removeLikeFromReview(reviewId);
    }
}
