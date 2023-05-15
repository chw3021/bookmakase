package io.github.chw3021.bookmakase.review.service;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.repository.BookRepository;
import io.github.chw3021.bookmakase.bookdata.service.BookService;
import io.github.chw3021.bookmakase.interparkapi.service.InterparkApiService;
import io.github.chw3021.bookmakase.review.domain.Comment;
import io.github.chw3021.bookmakase.review.domain.Report;
import io.github.chw3021.bookmakase.review.domain.Review;
import io.github.chw3021.bookmakase.review.domain.dto.CommentDto;
import io.github.chw3021.bookmakase.review.domain.dto.ReportDto;
import io.github.chw3021.bookmakase.review.domain.dto.ReviewDto;
import io.github.chw3021.bookmakase.review.repository.CommentRepository;
import io.github.chw3021.bookmakase.review.repository.ReportRepository;
import io.github.chw3021.bookmakase.review.repository.ReviewRepository;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
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
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final BookService bookService;
    @Autowired
    private final InterparkApiService interparkApiService;

    
    public Review save(ReviewDto reviewDto) {
        if(!bookService.isBookExist(reviewDto.getItemId())){
            bookService.saveBook(interparkApiService.getBookSearchResultsByItemId(reviewDto.getItemId()).getItem().get(0));
        }
        Book book = bookRepository.findById(reviewDto.getItemId()).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + reviewDto.getItemId()));
        Member member = memberRepository.findById(reviewDto.getMemberId()).orElseThrow(() -> new IllegalArgumentException("Invalid member id: " + reviewDto.getMemberId()));
        Review review = Review.builder()
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .rating(reviewDto.getRating())
                .likes(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .book(book)
                .member(member)
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

    public Review update(Long id, ReviewDto reviewDto) {
        if(!bookService.isBookExist(reviewDto.getItemId())){
            bookService.saveBook(interparkApiService.getBookSearchResultsByItemId(reviewDto.getItemId()).getItem().get(0));
        }
        Book book = bookRepository.findById(reviewDto.getItemId()).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + reviewDto.getItemId()));
        Member member = memberRepository.findById(reviewDto.getMemberId()).orElseThrow(() -> new IllegalArgumentException("Invalid member id: " + reviewDto.getMemberId()));
        
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + id));
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());
        review.setBook(book);
        review.setMember(member);
        review.setLikes(reviewDto.getLikes());
        review.setUpdatedAt(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }
    
    public List<Report> findAllByReviewId(Long reviewId) {
        return reportRepository.findAllByReviewId(reviewId);
    }
    public List<Comment> getCommentsByReviewId(Long reviewId) {
        return commentRepository.findByReviewId(reviewId);
    }
    
    public Report reportReview(ReportDto reportDto) {
    	Long reviewId = reportDto.getReviewId();
        Member member = memberRepository.findById(reportDto.getMemberId()).orElseThrow(() -> new IllegalArgumentException("Invalid member id: " + reportDto.getMemberId()));

    	Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + reviewId));;
        Report report = Report.builder()
        		.member(member)
        		.createdAt(LocalDateTime.now())
        		.processed(false)
        		.reason(reportDto.getReason())
        		.review(review)
        		.build();
		reportRepository.save(report);
        return report;
    }

    public Report processReport(Long reportId, Integer process) {
    	Optional<Report> optionalReport = reportRepository.findById(reportId);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            report.setProcessed(true);
            report.setProcessedResult(process);
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
        return commentRepository.save(comment);
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

	public List<Report> getReviewReports(Long reviewId) {
		return reportRepository.findAllByReviewId(reviewId);
	}

}
