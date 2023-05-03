package io.github.chw3021.bookmakase.review.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.chw3021.bookmakase.review.domain.Comment;
import io.github.chw3021.bookmakase.review.domain.dto.CommentDto;
import io.github.chw3021.bookmakase.review.service.CommentService;

@RestController
@RequestMapping("/reviews/{reviewId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{commentId}")
    public List<Comment> getComments(@PathVariable Long reviewId) {
        return commentService.getCommentsByReviewId(reviewId);
    }

    @PostMapping("/{commentId}")
    public Comment addComment(@PathVariable Long reviewId, @RequestBody CommentDto commentDto) {
        return commentService.addCommentToReview(reviewId, commentDto);
    }
}