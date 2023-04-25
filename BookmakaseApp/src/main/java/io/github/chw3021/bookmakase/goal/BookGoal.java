package io.github.chw3021.bookmakase.goal;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "book_goal")
public class BookGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private GoalUser goaluser;
    
    private Long categoryId;
    private Integer targetQuantity;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean completed;


    public BookGoal() {
    }

    @Builder
    public BookGoal(GoalUser goaluser, Long categoryId, int targetQuantity, LocalDate startDate, LocalDate endDate, Boolean completed) {
        this.goaluser = goaluser;
        this.categoryId = categoryId;
        this.targetQuantity = targetQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completed = completed;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GoalUser getGoalUser() {
        return goaluser;
    }

    public void setGoalUser(GoalUser goaluser) {
        this.goaluser = goaluser;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public int getTargetQuantity() {
        return targetQuantity;
    }

    public void setTargetQuantity(int targetQuantity) {
        this.targetQuantity = targetQuantity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean isCompleted() {
    	return completed;
    }
    public void setCompleted(Boolean completed) {
    	this.completed = completed;
    }

}