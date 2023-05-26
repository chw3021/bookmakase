package io.github.chw3021.bookmakase.review.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import io.github.chw3021.bookmakase.review.domain.Heart;
import io.github.chw3021.bookmakase.review.repository.HeartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.repository.BookRepository;
import io.github.chw3021.bookmakase.bookdata.service.BookService;
import io.github.chw3021.bookmakase.interparkapi.service.InterparkApiService;
import io.github.chw3021.bookmakase.review.domain.Report;
import io.github.chw3021.bookmakase.review.domain.Review;
import io.github.chw3021.bookmakase.review.domain.dto.ReportDto;
import io.github.chw3021.bookmakase.review.domain.dto.ReviewDto;
import io.github.chw3021.bookmakase.review.repository.ReportRepository;
import io.github.chw3021.bookmakase.review.repository.ReviewRepository;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
	
    @Autowired
    private final ReviewRepository reviewRepository;
    @Autowired
    private final ReportRepository reportRepository;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final BookService bookService;
    @Autowired
    private final InterparkApiService interparkApiService;
    @Autowired
    private final HeartRepository heartRepository;

    
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
    	List<Review> all = reviewRepository.findAll();
    	List<Report> reported = reportRepository.findAll().stream().filter(r -> r.getProcessed() && r.getProcessedResult()!=0).toList();
    	//List<Review> sort = all.stream().filter(r -> reported.stream().anyMatch(rp -> rp.getReview()==r)).toList();
    	all.removeIf(r -> reported.stream().anyMatch(rp -> rp.getReview()==r));
    	
        return all;
    }

    public List<Review> search(String param) {
    	List<Review> all = findAll();
        return all.stream().filter(r -> {
            Boolean title = r.getTitle().contains(param);
            Boolean user = r.getMember().getAccount().contains(param);
            Boolean un = r.getMember().getName().contains(param);
            Boolean con = r.getContent().contains(param);
            Boolean bt = r.getBook().getTitle().contains(param);
            Boolean ba = r.getBook().getAuthor().contains(param);
            return title||user||un||con||bt||ba;
        }).toList();
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

    public void delete(Long reviewId) {
        List<Report> reports = reportRepository.findAllByReviewId(reviewId);
        for (Report report : reports) {
            reportRepository.delete(report);
        }
        reviewRepository.deleteById(reviewId);
    }

    public void deleteByMember(Long memberId){
        reviewRepository.findAllByMemberId(memberId).forEach(r ->{
            delete(r.getId());
        });
    }
    public void heartDeleteById(Long memberId){
        heartRepository.findAllByMemberId(memberId).forEach(r -> heartRepository.deleteById(r.getId())

        );
    }

    public List<Report> findAllByReviewId(Long reviewId) {
        return reportRepository.findAllByReviewId(reviewId);
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
        		.content(reportDto.getContent())
        		.review(review)
        		.build();
		reportRepository.save(report);
        return report;
    }

    public Report processReport(Long reportId, Integer process) throws Exception {
    	Optional<Report> optionalReport = reportRepository.findById(reportId);
        if (optionalReport.isPresent()) {
            Report report = optionalReport.get();
            report.setProcessed(true);
            report.setProcessedResult(process);
            Member m = report.getReview().getMember();
            if(process == 1) {
            	delete(report.getReview().getId());
            	
            	m.setWarned(m.getWarned()+1);
            	if(m.getWarned()>=3&&m.getWarned()<5) {
                    LocalDateTime banned = LocalDateTime.now().plusDays(15);
                    m.setBan(banned);
                    memberRepository.save(m);
            	}
            	if(m.getWarned()>=5&&m.getWarned()<10) {
                    LocalDateTime banned = LocalDateTime.now().plusDays(30);
                    m.setBan(banned);
                    memberRepository.save(m);
            	}
            	if(m.getWarned()>=10) {
                    LocalDateTime banned = LocalDateTime.now().plusYears(1);
                    m.setBan(banned);
                    memberRepository.save(m);
            	}
            }
            else if(process == 2) {
            	delete(report.getReview().getId());
                LocalDateTime banned = LocalDateTime.now().plusYears(999);
                m.setBan(banned);
                memberRepository.save(m);
            }
            else {
            	delete(report.getReview().getId());
            }
            return report;
        } else {
            throw new IllegalStateException("Report with id " + reportId + " does not exist.");
        }
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
    



    public Heart addLikeToReview(Long reviewId, Long memberId) {
        Review review = getReviewById(reviewId);

        if(isLiked(reviewId,memberId)){
            Heart heart = heartRepository.findAllByMemberId(memberId).stream().filter(l -> l.getReview() == review).findFirst().get();
            heartRepository.delete(heart);
            review.setLikes(review.getLikes() - 1);
            reviewRepository.save(review);
            return heart;
        }
        else{
            Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("Invalid member id: " + memberId));

            Heart heart = Heart.builder()
                            .review(review)
                            .member(member)
                        .build();
            heartRepository.save(heart);

            review.setLikes(review.getLikes() + 1);
            reviewRepository.save(review);
            return heart;
        }
    }

    public List<Heart> getMemberLike(Long memberId){

        return heartRepository.findAllByMemberId(memberId);
    }

    public boolean isLiked(Long reviewId, Long memberId){
        Review review = getReviewById(reviewId);

        return heartRepository.findAllByMemberId(memberId).stream().anyMatch(l -> l.getReview() == review);
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
