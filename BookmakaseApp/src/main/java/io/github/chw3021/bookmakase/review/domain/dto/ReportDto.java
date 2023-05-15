package io.github.chw3021.bookmakase.review.domain.dto;

import java.time.LocalDateTime;

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
public class ReportDto {

    private Long id;

    private String reason;

    private LocalDateTime createdAt;
    private Long memberId;
    private Long reviewId;
    private Boolean processed;//신고 처리 여부
    
    private String processedResult;//신고 처리 결과(경고:warned, 제재:penalized)
}
