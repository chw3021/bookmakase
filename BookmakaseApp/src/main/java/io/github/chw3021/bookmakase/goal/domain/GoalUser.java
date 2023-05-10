package io.github.chw3021.bookmakase.goal.domain;

import java.util.ArrayList;
import java.util.List;

import io.github.chw3021.bookmakase.signservice.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("GoalUser") @AllArgsConstructor @NoArgsConstructor
public class GoalUser extends Member{
	
	//Member를 상속받아 독서 목표 사용자 클래스를 생성
	//다른 사용자들의 목표 달성량을 비교할떄 사용할 수 있다.
	
	@OneToMany(mappedBy = "goaluser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<BookGoal> bookGoals = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    
	public void addBookGoal(BookGoal bookGoal) {
	    this.bookGoals.add(bookGoal);
	}

	public void removeBookGoal(BookGoal bookGoal) {
	    this.bookGoals.remove(bookGoal);
	}

    public List<BookGoal> getBookGoals() {
        return bookGoals;
    }
    public void setBookGoals(List<BookGoal> bookGoals) {
        this.bookGoals = bookGoals;
    }

}