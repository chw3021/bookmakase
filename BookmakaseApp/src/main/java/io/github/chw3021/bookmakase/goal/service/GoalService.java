package io.github.chw3021.bookmakase.goal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.chw3021.bookmakase.goal.domain.BookGoal;
import io.github.chw3021.bookmakase.goal.domain.GoalUser;
import io.github.chw3021.bookmakase.goal.dto.BookGoalDto;
import io.github.chw3021.bookmakase.goal.repository.BookGoalRepository;
import io.github.chw3021.bookmakase.goal.repository.GoalUserRepository;
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
    private GoalUserRepository goaluserRepository;

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
    

    public List<BookGoal> getUserGoals(Long id) {

        Optional<GoalUser> memberOpt = goaluserRepository.findById(id);
        if (memberOpt.isPresent()) {
        	GoalUser goalUser = memberOpt.get();
                return goalUser.getBookGoals();
                // bookGoals를 이용하여 독서 목표에 대한 작업 수행
        }
        return null;

    }
    
    //같은 나이대, 같은 카테고리에 대한 독서 목표를 완료한 사용자 수를 받는 메서드
   public int getNumberOfUsersInSameAgeAndCategory(GoalUser goalUser, Long categoryId) {
        int numOfUsers = 0;
        List<GoalUser> usersInSameAgeRange = goaluserRepository.findAllByAgeBetween(goalUser.getAge() - 5, goalUser.getAge() + 5);
        for (GoalUser user : usersInSameAgeRange) {
                List<BookGoal> bookGoals = user.getBookGoals();
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