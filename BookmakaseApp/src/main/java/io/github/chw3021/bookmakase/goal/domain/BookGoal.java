package io.github.chw3021.bookmakase.goal.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Member member;//멤버아이디
    
    private String goalname;//목표 이름
    private Integer categoryId;//도서 카테고리 아이디
    private Integer targetQuantity;//목표 도서량
    private Integer readed;//읽은책 갯수
    private LocalDate startDate;//시작일
    private LocalDate endDate;//목표일
    private Boolean completed;//완료여부


	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
    

}