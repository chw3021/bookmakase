package io.github.chw3021.bookmakase.signservice.repository;

import java.util.List;
import java.util.Optional;

import io.github.chw3021.bookmakase.signservice.domain.Member;

public interface MemberRepositoryCustom {
    public Optional<Member> findByAccount(String account);
    
    Member save(Member member); //DB에 저장
    Optional<Member> findByName(String name); //
    
    List<Member> findMembersByAgeBetween(int minAge, int maxAge);
}
