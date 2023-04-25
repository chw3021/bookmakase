package io.github.chw3021.bookmakase.goal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.chw3021.bookmakase.goal.domain.BookGoal;
import io.github.chw3021.bookmakase.goal.dto.BookGoalDto;
import io.github.chw3021.bookmakase.goal.repository.BookGoalRepository;
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
}