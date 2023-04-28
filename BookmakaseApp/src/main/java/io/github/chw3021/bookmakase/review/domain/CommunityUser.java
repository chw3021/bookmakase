package io.github.chw3021.bookmakase.review.domain;

import java.util.List;

import io.github.chw3021.bookmakase.signservice.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class CommunityUser extends Member {

    @Column
    private int reportCount; // 신고 누적 횟수

    @OneToMany(mappedBy = "communityuser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Review> reviews; // 작성한 리뷰 목록

}
