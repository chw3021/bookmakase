package io.github.chw3021.bookmakase.review.domain.dto;

import io.github.chw3021.bookmakase.signservice.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentDto {
    private String content;
    private Member member;
}