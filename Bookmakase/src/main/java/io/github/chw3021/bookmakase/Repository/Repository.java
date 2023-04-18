package io.github.chw3021.bookmakase.Repository;

import io.github.chw3021.bookmakase.domain.Member;
import java.util.List;
import java.util.Optional;

public interface Repository extends JpaRepository<Member, Long> { //jparepository를 상속받는 repositroy 인터페이스 생성
    Member save(Member member); //DB에 저장
    Optional<Member> findById(Long id); //ID로 찾기
    Optional<Member> findByName(String name); //
    List<Member> findAll(); //전체 불러오기


}