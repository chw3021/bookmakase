package io.github.chw3021.bookmakase.goal.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import io.github.chw3021.bookmakase.goal.repository.GoalUserRepository;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("GoalUser") @AllArgsConstructor @NoArgsConstructor
public class GoalUser extends Member {
	
	//Member를 상속받아 독서 목표 사용자 클래스를 생성
	//다른 사용자들의 목표 달성량을 비교할떄 사용할 수 있다.
	
	@OneToMany(mappedBy = "goaluser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<BookGoal> bookGoals = new ArrayList<>();

    
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