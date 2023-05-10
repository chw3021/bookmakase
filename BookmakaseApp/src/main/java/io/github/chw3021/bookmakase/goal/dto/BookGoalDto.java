package io.github.chw3021.bookmakase.goal.dto;

import java.time.LocalDate;
import io.github.chw3021.bookmakase.goal.domain.BookGoal;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder @AllArgsConstructor
public class BookGoalDto {
    private Member member;
    private Long id;
    private Long userId;
    private Long categoryId;
    private Integer targetQuantity;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean completed;

    public BookGoalDto() {
    }

    public BookGoalDto(BookGoal bookGoal) {
    	this.member = bookGoal.getMember();
        this.id = bookGoal.getId();
        this.categoryId = bookGoal.getCategoryId();
        this.targetQuantity = bookGoal.getTargetQuantity();
        this.startDate = bookGoal.getStartDate();
        this.endDate = bookGoal.getEndDate();
        this.completed = bookGoal.isCompleted();
    }

    public BookGoal toEntity() {
        return BookGoal.builder()
                .member(member)
                .categoryId(categoryId)
                .targetQuantity(targetQuantity)
                .startDate(startDate)
                .endDate(endDate)
                .completed(completed)
                .build();
    }
    // Getter and Setter
}
