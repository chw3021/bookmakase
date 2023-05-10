package io.github.chw3021.bookmakase.goal.domain;

import java.time.LocalDate;

import io.github.chw3021.bookmakase.signservice.domain.Member;
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
    @JoinColumn(name = "memberId")
    private Member member;
    
    private Long categoryId;
    private Integer targetQuantity;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean completed;


    public BookGoal() {
    }

    @Builder
    public BookGoal(Member member, Long categoryId, int targetQuantity, LocalDate startDate, LocalDate endDate, Boolean completed) {
        this.member = member;
        this.categoryId = categoryId;
        this.targetQuantity = targetQuantity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completed = completed;
    }

	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
    

}