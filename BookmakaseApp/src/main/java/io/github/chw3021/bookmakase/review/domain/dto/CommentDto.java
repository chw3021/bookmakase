package io.github.chw3021.bookmakase.review.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentDto {
    private String content;
    private Long userId;
}