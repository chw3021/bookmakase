package io.github.chw3021.bookmakase.review.domain;

import io.github.chw3021.bookmakase.signservice.domain.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Heart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;
    
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
}