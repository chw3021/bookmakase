package io.github.chw3021.bookmakase.goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

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
	
	@OneToMany(mappedBy = "goalUser", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<BookGoal> bookGoals = new ArrayList<>();

    private MemberRepository memberRepository;

    @Autowired
    public GoalUser(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
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

    public List<BookGoal> getUserGoals(Long id) {

        Optional<Member> memberOpt = memberRepository.findById(id);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            if (member instanceof GoalUser) {
                GoalUser goalUser = (GoalUser) member;
                return goalUser.getBookGoals();
                // bookGoals를 이용하여 독서 목표에 대한 작업 수행
            }
        }
        return null;

    }
    
}