package io.github.chw3021.bookmakase.signservice.repository;

import java.util.List;
import java.util.Optional;

import io.github.chw3021.bookmakase.goal.GoalUser;
import io.github.chw3021.bookmakase.signservice.domain.Member;

public interface MemberRepositoryCustom {
    public Optional<Member> findByAccount(String account);
    
    Member save(Member member); //DB에 저장
    Optional<Member> findById(Long id); //ID로 찾기
    Optional<Member> findByName(String name); //
    List<Member> findAll(); //전체 불러오기
    
    List<Member> findMembersByAgeBetween(int minAge, int maxAge);
}
