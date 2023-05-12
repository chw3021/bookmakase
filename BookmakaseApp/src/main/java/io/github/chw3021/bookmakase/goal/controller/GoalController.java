package io.github.chw3021.bookmakase.goal.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.chw3021.bookmakase.goal.domain.BookGoal;
import io.github.chw3021.bookmakase.goal.dto.BookGoalDto;
import io.github.chw3021.bookmakase.goal.service.GoalService;

@RestController
@RequestMapping("/goal")
public class GoalController {

    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping
    public ResponseEntity<BookGoal> createGoal(@RequestBody BookGoalDto goalDto) throws Exception {
        BookGoal createdGoalDto = goalService.createGoal(goalDto);
        return ResponseEntity.ok(createdGoalDto);
    }

    @GetMapping("/getById")
    public ResponseEntity<BookGoal> getGoalById(@RequestParam Long id) {
        BookGoal goal = goalService.getGoalById(id);
        return ResponseEntity.ok(goal);
    }


    @GetMapping("/getByMemberId")
    public ResponseEntity<List<BookGoal>> getGoalsByMemberId(@RequestParam Long memberId) {
        List<BookGoal> goal = goalService.getUserGoals(memberId);
        return ResponseEntity.ok(goal);
    }

    @GetMapping("/similar")
    public ResponseEntity<Integer> getGoalsByMemberId(@RequestParam Long memberId, @RequestParam Integer categoryId) throws Exception {
        Integer count = goalService.getNumberOfUsersInSameAgeAndCategory(memberId, categoryId);
        return ResponseEntity.ok(count);
    }
    
    
    @PutMapping("/setReaded")
    public ResponseEntity<BookGoal> setReadedById(@RequestParam Long id, @RequestParam Integer readed) {
        BookGoal goal = goalService.setReadedById(id,readed);
        return ResponseEntity.ok(goal);
    }
    
    @DeleteMapping("/deleteByMember")
    public ResponseEntity<Void> deleteByMember(@RequestParam Long memberId) {
        goalService.deleteAllUserGoalsByMemberId(memberId);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteGoal(@RequestParam Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }
}