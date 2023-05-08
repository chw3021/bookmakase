package io.github.chw3021.bookmakase.goal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.chw3021.bookmakase.goal.dto.BookGoalDto;
import io.github.chw3021.bookmakase.goal.service.GoalService;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping
    public ResponseEntity<BookGoalDto> createGoal(@RequestBody @Validated BookGoalDto goalDto) {
        BookGoalDto createdGoalDto = goalService.createGoal(goalDto);
        return ResponseEntity.ok(createdGoalDto);
    }

    @GetMapping("/goal/{id}")
    public ResponseEntity<BookGoalDto> getGoalById(@PathVariable Long id) {
        BookGoalDto goalDto = goalService.getGoalById(id);
        return ResponseEntity.ok(goalDto);
    }

    @PutMapping("/goal/update/{id}")
    public ResponseEntity<BookGoalDto> updateGoal(@PathVariable Long id, @RequestBody @Validated BookGoalDto goalDto) {
        BookGoalDto updatedGoalDto = goalService.updateGoal(id, goalDto);
        return ResponseEntity.ok(updatedGoalDto);
    }

    @DeleteMapping("/goal/delete/{id}")
    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }
}