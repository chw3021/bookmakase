package io.github.chw3021.bookmakase.repository;

import io.github.chw3021.bookmakase.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom { //jparepository를 상속받는 repositroy 인터페이스 생성


}