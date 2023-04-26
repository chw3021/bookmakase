package io.github.chw3021.bookmakase.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.chw3021.bookmakase.review.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>{

	List<Comment> findByReviewId(Long reviewId);

}
