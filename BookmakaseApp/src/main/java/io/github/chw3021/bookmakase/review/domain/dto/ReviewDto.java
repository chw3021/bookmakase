package io.github.chw3021.bookmakase.review.domain.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.review.domain.Comment;
import io.github.chw3021.bookmakase.review.domain.CommunityUser;
import io.github.chw3021.bookmakase.review.domain.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private double rating;
    private int likes;
    
    private List<Report> reports;

    private List<Comment> comments;
    
    
    private CommunityUser communityuser;
    
    private Book book;
}