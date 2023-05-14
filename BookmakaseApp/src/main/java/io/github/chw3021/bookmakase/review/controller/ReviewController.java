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
    

    @GetMapping("/findAll")
    public List<Review> findAll() {
        return reviewService.findAll();
    }

    @GetMapping("/findById")
    public Review findById(@RequestParam Long id) {
        return reviewService.findById(id);
    }

    @PostMapping("/save")
    public Review save(@RequestBody ReviewDto reviewDto) {
        return reviewService.save(reviewDto);
    }

    @PutMapping("/update")
    public Review update(@RequestParam Long id, @RequestBody ReviewDto reviewDto) {
        return reviewService.update(id, reviewDto);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam Long id) {
        reviewService.delete(id);
    }

    @PostMapping("/reportReview")
    public void reportReview(@RequestParam Long reviewId, @RequestBody ReportDto reportDto) {
    	reviewService.reportReview(reviewId, reportDto);
    }

    @GetMapping("/reports")
    public List<Report> getReviewReports(@RequestParam Long reviewId) {
        return reviewService.getReviewReports(reviewId);
    }
    @GetMapping("/reports")
    public List<Report> getAllReports() {
        return reviewService.getAllReports();
    }

    
    @PostMapping("/addComments")
    public void addCommentToReview(@RequestParam Long reviewId, @RequestBody CommentDto commentDto) {
        reviewService.addCommentToReview(reviewId, commentDto);
    }

    @GetMapping("/getComments")
    public List<Comment> getCommentsOfReview(@RequestParam Long reviewId) {
        return reviewService.getCommentsByReviewId(reviewId);
    }

    @PostMapping("/addLike")
    public void addLikeToReview(@RequestParam Long reviewId) {
        reviewService.addLikeToReview(reviewId);
    }

    @DeleteMapping("/removeLike")
    public void removeLikeFromReview(@RequestParam Long reviewId) {
        reviewService.removeLikeFromReview(reviewId);
    }
}
