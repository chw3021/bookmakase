package io.github.chw3021.bookmakase.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.chw3021.bookmakase.review.domain.CommunityUser;

@Repository
public interface CommunityUserRepository extends JpaRepository<CommunityUser,Long>{

}
