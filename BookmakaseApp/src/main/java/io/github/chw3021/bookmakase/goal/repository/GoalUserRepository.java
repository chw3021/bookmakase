package io.github.chw3021.bookmakase.goal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.chw3021.bookmakase.goal.domain.GoalUser;

import java.util.List;

@Repository
public interface GoalUserRepository extends JpaRepository<GoalUser, Long> {

    List<GoalUser> findAllByAgeBetween(int minAge, int maxAge);
}