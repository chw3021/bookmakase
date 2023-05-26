package io.github.chw3021.bookmakase.review.controller;

import io.github.chw3021.bookmakase.review.domain.Heart;
import io.github.chw3021.bookmakase.review.domain.Report;
import io.github.chw3021.bookmakase.review.domain.Review;
import io.github.chw3021.bookmakase.review.domain.dto.ReportDto;
import io.github.chw3021.bookmakase.review.domain.dto.ReviewDto;
import io.github.chw3021.bookmakase.review.service.ReviewService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public void delete(@RequestParam Long reviewId) {
        reviewService.delete(reviewId);
    }

    @PostMapping("/reportReview")
    public ResponseEntity<Report> reportReview(@RequestBody ReportDto reportDto) {
    	Report r = reviewService.reportReview(reportDto);
        
    	return ResponseEntity.ok(r);
    }

    //1:경고 횟수 추가(3회시 15일정지, 5회시 30일 정지), 2: 영구정지, 0:무고
    @PostMapping("/reportProcess")
    public ResponseEntity<Report> reportProcess(@RequestParam Long reportId, @RequestParam Integer process) throws Exception {
    	return new ResponseEntity<>(reviewService.processReport(reportId, process), HttpStatus.OK);
    }


    @GetMapping("/getReports")
    public List<Report> getReviewReports(@RequestParam Long reviewId) {
        return reviewService.getReviewReports(reviewId);
    }
    
    @GetMapping("/getAllreports")
    public List<Report> getAllReports() {
        return reviewService.getAllReports();
    }

    @GetMapping("/isLiked")
    public Boolean isLiked(@RequestParam Long reviewId, @RequestParam Long memberId) {
        return reviewService.isLiked(reviewId, memberId);
    }
    @GetMapping("/getMemberLike")
    public List<Heart> isLiked(@RequestParam Long memberId) {
        return reviewService.getMemberLike(memberId);
    }
    @PostMapping("/addLike")
    public Heart addLikeToReview(@RequestParam Long reviewId, @RequestParam Long memberId) {
        return reviewService.addLikeToReview(reviewId, memberId);
    }

    @PutMapping("/removeLike")
    public void removeLikeFromReview(@RequestParam Long reviewId) {
        reviewService.removeLikeFromReview(reviewId);
    }
}
