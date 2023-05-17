package io.github.chw3021.bookmakase.review.controller;

import io.github.chw3021.bookmakase.goal.service.BookShelfService;
import io.github.chw3021.bookmakase.review.domain.Comment;
import io.github.chw3021.bookmakase.review.domain.Report;
import io.github.chw3021.bookmakase.review.domain.Review;
import io.github.chw3021.bookmakase.review.domain.dto.CommentDto;
import io.github.chw3021.bookmakase.review.domain.dto.ReportDto;
import io.github.chw3021.bookmakase.review.domain.dto.ReviewDto;
import io.github.chw3021.bookmakase.review.service.ReviewService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    

    @GetMapping("/findAll")
    public List<Review> findAll() {
        return reviewService.findAll();
    }
    @GetMapping("/search")
    public ResponseEntity<List<Review>> findById(@RequestParam String param) {
    	List<Review> r = reviewService.search(param);
    	return ResponseEntity.ok(r);
    }

    @GetMapping("/findById")
    public ResponseEntity<Review> findById(@RequestParam Long id) {
    	Review r = reviewService.findById(id);
    	return ResponseEntity.ok(r);
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
    public ResponseEntity<Report> reportReview(@RequestBody ReportDto reportDto) {
    	Report r = reviewService.reportReview(reportDto);
        
    	return ResponseEntity.ok(r);
    }
    


    @GetMapping("/getReports")
    public List<Report> getReviewReports(@RequestParam Long reviewId) {
        return reviewService.getReviewReports(reviewId);
    }
    
    @GetMapping("/getAllreports")
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
