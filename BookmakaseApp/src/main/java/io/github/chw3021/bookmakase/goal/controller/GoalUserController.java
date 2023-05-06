package io.github.chw3021.bookmakase.goal.controller;

import io.github.chw3021.bookmakase.goal.domain.GoalUser;
import io.github.chw3021.bookmakase.goal.service.GoalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class GoalUserController {

    private final GoalUserService goalUserService;

    @Autowired
    public GoalUserController(GoalUserService goalUserService) {
        this.goalUserService = goalUserService;
    }

    @PostMapping
    public ResponseEntity<GoalUser> createGoalUser(@RequestBody GoalUser goalUser) {
        GoalUser createdGoalUser = goalUserService.createGoalUser(goalUser);
        return new ResponseEntity<>(createdGoalUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalUser> getGoalUserById(@PathVariable Long id) {
        GoalUser goalUser = goalUserService.getGoalUserById(id);
        if (goalUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(goalUser, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalUser> updateGoalUser(@PathVariable Long id, @RequestBody GoalUser goalUser) {
        GoalUser updatedGoalUser = goalUserService.updateGoalUser(id, goalUser);
        if (updatedGoalUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(updatedGoalUser, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoalUserById(@PathVariable Long id) {
        goalUserService.deleteGoalUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
