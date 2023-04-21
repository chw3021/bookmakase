package io.github.chw3021.bookmakase.repository;

import java.util.List;
import java.util.Optional;

import io.github.chw3021.bookmakase.domain.Member;

public interface MemberRepositoryCustom {
    public Optional<Member> findByAccount(String account);
    //Account로 member 찾기. account는 차후 논의
    
    Member save(Member member); //DB에 저장
    Optional<Member> findById(Long id); //ID로 찾기
    Optional<Member> findByName(String name); //
    List<Member> findAll(); //전체 불러오기
}
