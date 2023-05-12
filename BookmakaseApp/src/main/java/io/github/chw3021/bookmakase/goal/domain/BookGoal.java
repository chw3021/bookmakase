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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name = "book_goal")
@Builder @AllArgsConstructor @NoArgsConstructor
public class BookGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
    
    private String goalname;
    private Integer categoryId;
    private Integer targetQuantity;
    private Integer readed;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean completed;


	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
    

}