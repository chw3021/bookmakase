package io.github.chw3021.bookmakase.goal.service;

import io.github.chw3021.bookmakase.goal.domain.GoalUser;
import io.github.chw3021.bookmakase.goal.repository.GoalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GoalUserService {

    private final GoalUserRepository goalUserRepository;

    @Autowired
    public GoalUserService(GoalUserRepository goalUserRepository) {
        this.goalUserRepository = goalUserRepository;
    }

    public GoalUser createGoalUser(GoalUser goalUser) {
        return goalUserRepository.save(goalUser);
    }

    public GoalUser getGoalUserById(Long id) {
        Optional<GoalUser> optionalGoalUser = goalUserRepository.findById(id);
        return optionalGoalUser.orElse(null);
    }

    public GoalUser updateGoalUser(Long id, GoalUser goalUser) {
        GoalUser currentGoalUser = getGoalUserById(id);
        if (currentGoalUser != null) {
            currentGoalUser.setName(goalUser.getName());
            currentGoalUser.setEmail(goalUser.getEmail());
            currentGoalUser.setPassword(goalUser.getPassword());
            return goalUserRepository.save(currentGoalUser);
        } else {
            return null;
        }
    }

    public void deleteGoalUserById(Long id) {
        goalUserRepository.deleteById(id);
    }
}
