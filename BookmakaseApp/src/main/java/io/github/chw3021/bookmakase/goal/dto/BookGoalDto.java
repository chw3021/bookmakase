package io.github.chw3021.bookmakase.goal.dto;

import java.time.LocalDate;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder @AllArgsConstructor
public class BookGoalDto {
    
    private Long memberId;

    private Long id;
    private String goalname;
    private Integer categoryId;
    private Integer targetQuantity;
    private Integer readed;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean completed;

    public void setMember(Member member) {
        this.memberId = member.getId();
    }

    public Member getMember(MemberRepository memberRepository) {
        return memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));
    }
    // Getter and Setter
}
