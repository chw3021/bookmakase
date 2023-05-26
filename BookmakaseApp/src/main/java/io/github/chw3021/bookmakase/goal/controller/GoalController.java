package io.github.chw3021.bookmakase.goal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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


    @GetMapping("/getByGoalname")
    public ResponseEntity<?> getGoalByGoalname(@RequestParam String goalname) {
        BookGoal goal = goalService.getGoalByGoalname(goalname);
        if (goal == null) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(goal);
    }
    @GetMapping("/isExist")
    public boolean isGoalExist(@RequestParam String goalname) {
        return goalService.isGoalExist(goalname);
    }

    @GetMapping("/getByMemberId")
    public ResponseEntity<List<BookGoal>> getGoalsByMemberId(@RequestParam Long memberId) {
        List<BookGoal> goal = goalService.getUserGoals(memberId);
        return ResponseEntity.ok(goal);
    }

    @GetMapping("/getCompletedCount")
    public ResponseEntity<Long> getCompletedCount(@RequestParam Long memberId) throws Exception {
        Long count = goalService.getCompletedCount(memberId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/getSuccessRate")
    public ResponseEntity<Long> getSuccessRate(@RequestParam Long memberId) throws Exception {
        Long count = goalService.getSuccessRate(memberId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/getAverageRate")
    public ResponseEntity<Long> getAverageRate(@RequestParam Long memberId) throws Exception {
        Long count = goalService.getAverageRateOfSimilar(memberId);
        return ResponseEntity.ok(count);
    }
    @GetMapping("/similarCompletedsAll")
    public ResponseEntity<Integer> similarCompleteds(@RequestParam Long memberId) throws Exception {
        Integer count = goalService.getNumberOfSimilarUsersCompleted(memberId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/similarChallengersAll")
    public ResponseEntity<Integer> similarChallengers(@RequestParam Long memberId) throws Exception {
        Integer count = goalService.getNumberOfSimilarChallengers(memberId);
        return ResponseEntity.ok(count);
    }
    @GetMapping("/similarCompleteds")
    public ResponseEntity<Integer> similarCompleteds(@RequestParam Long memberId, @RequestParam Integer categoryId) throws Exception {
        Integer count = goalService.getNumberOfSimilarUsersCompleted(memberId, categoryId);
        return ResponseEntity.ok(count);
    }
    @GetMapping("/isCompleted")
    public ResponseEntity<Boolean> isCompleted(@RequestParam Long goalId) throws Exception {
        return ResponseEntity.ok(goalService.isCompleted(goalId));
    }

    @GetMapping("/similarChallengers")
    public ResponseEntity<Integer> similarChallengers(@RequestParam Long memberId, @RequestParam Integer categoryId) throws Exception {
        Integer count = goalService.getNumberOfSimilarChallengers(memberId, categoryId);
        return ResponseEntity.ok(count);
    }

    @PutMapping
    public ResponseEntity<BookGoal> updateGoal(@RequestBody BookGoalDto goalDto) throws Exception {
        BookGoal createdGoalDto = goalService.createGoal(goalDto);
        return ResponseEntity.ok(createdGoalDto);
    }
    
    @PutMapping("/setReaded")//목표 고유 아이디를 넣어서 몇권읽었는지 설정
    public ResponseEntity<BookGoal> setReadedById(@RequestParam Long id, @RequestParam Integer readed) {
        BookGoal goal = goalService.setReadedById(id,readed);
        return ResponseEntity.ok(goal);
    }

    @PutMapping("/update")
    public ResponseEntity<BookGoal> update(@RequestBody BookGoalDto goalDto) throws Exception {
        BookGoal createdGoalDto = goalService.updateGoal(goalDto);
        return ResponseEntity.ok(createdGoalDto);
    }

    @DeleteMapping("/deleteByMember")//멤버 아이디 집어넣어서 그멤버가 가진 모든 목표 삭제
    public ResponseEntity<Void> deleteByMember(@RequestParam Long memberId) {
        goalService.deleteAllUserGoalsByMemberId(memberId);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/delete")//목표 고유번호로 삭제
    public ResponseEntity<Void> deleteGoal(@RequestParam Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }
}