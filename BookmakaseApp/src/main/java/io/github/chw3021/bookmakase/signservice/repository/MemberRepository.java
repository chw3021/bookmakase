package io.github.chw3021.bookmakase.signservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.chw3021.bookmakase.signservice.domain.Member;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> { //jparepository를 상속받는 repositroy 인터페이스 생성

	Optional<Member> findByAccount(String account);
    Optional<Member> findByemail(String email);
	
    List<Member> findByAgeBetween(int minAge, int maxAge);

}