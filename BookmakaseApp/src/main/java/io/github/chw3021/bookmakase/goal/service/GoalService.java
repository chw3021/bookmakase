package io.github.chw3021.bookmakase.goal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.chw3021.bookmakase.goal.domain.BookGoal;
import io.github.chw3021.bookmakase.goal.dto.BookGoalDto;
import io.github.chw3021.bookmakase.goal.repository.BookGoalRepository;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class GoalService {

    private final BookGoalRepository goalRepository;

    @Autowired
    public GoalService(BookGoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Autowired
    private MemberRepository memberRepository;

    public BookGoalDto createGoal(BookGoalDto goalDto) {
        BookGoal goal = goalDto.toEntity();
        BookGoal createdGoal = goalRepository.save(goal);
        return new BookGoalDto(createdGoal);
    }

    public BookGoalDto getGoalById(Long id) {
    	BookGoal goal = goalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find goal with id: " + id));
        return new BookGoalDto(goal);
    }

    public BookGoalDto updateGoal(Long id, BookGoalDto goalDto) {
        BookGoal goal = goalDto.toEntity();
        goal.setId(id);
        BookGoal updatedGoal = goalRepository.save(goal);
        return new BookGoalDto(updatedGoal);
    }

    public void deleteGoal(Long id) {
        goalRepository.deleteById(id);
    }
    

    public List<BookGoal> getUserGoals(Long memberId) {
        return goalRepository.findAllByMemberId(memberId);

    }
    
    //같은 나이대, 같은 카테고리에 대한 독서 목표를 완료한 사용자 수를 받는 메서드
    public int getNumberOfUsersInSameAgeAndCategory(Long memberId, Long categoryId) throws Exception {
        int numOfUsers = 0;
        Member member = memberRepository.findById(memberId).orElseThrow(()->
        new Exception("계정을 찾을 수 없습니다."));
        List<Member> usersInSameAgeRange = memberRepository.findAllByAgeBetween(member.getAge() - 5, member.getAge() + 5);
        for (Member user : usersInSameAgeRange) {
            List<BookGoal> bookGoals = goalRepository.findAllByMemberId(user.getId());
            for (BookGoal bookGoal : bookGoals) {
                if (bookGoal.getCategoryId().equals(categoryId) && bookGoal.isCompleted()) {
                    numOfUsers++;
                    break;
                }
            }
        }
        return numOfUsers;
    }
}