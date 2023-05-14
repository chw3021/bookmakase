package io.github.chw3021.bookmakase.goal.dto;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.goal.domain.BookProgress;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder @AllArgsConstructor
public class LikedBookShelfDto {

    private Long id;
    
    private Long memberId;

    private List<Book> wantToRead = new ArrayList<>();

    public void setMember(Member member) {
        this.memberId = member.getId();
    }

    public Member getMember(MemberRepository memberRepository) {
        return memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member with id " + memberId + " not found"));
    }
}
