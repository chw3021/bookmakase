package io.github.chw3021.bookmakase.goal.dto;

import java.time.LocalDate;
import io.github.chw3021.bookmakase.goal.domain.BookGoal;
import io.github.chw3021.bookmakase.goal.domain.GoalUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder @AllArgsConstructor
public class BookGoalDto {
	private GoalUser goalUser;
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
    	this.goalUser = bookGoal.getGoalUser();
        this.id = bookGoal.getId();
        this.userId = bookGoal.getGoalUser().getId();
        this.categoryId = bookGoal.getCategoryId();
        this.targetQuantity = bookGoal.getTargetQuantity();
        this.startDate = bookGoal.getStartDate();
        this.endDate = bookGoal.getEndDate();
        this.completed = bookGoal.isCompleted();
    }

    public BookGoal toEntity() {
        return BookGoal.builder()
                .goaluser(goalUser)
                .categoryId(categoryId)
                .targetQuantity(targetQuantity)
                .startDate(startDate)
                .endDate(endDate)
                .completed(completed)
                .build();
    }
    // Getter and Setter
}
