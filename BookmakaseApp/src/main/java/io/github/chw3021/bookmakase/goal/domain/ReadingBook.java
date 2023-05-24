package io.github.chw3021.bookmakase.goal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class ReadingBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)//(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "itemId")
    private Book book;

    
    private Integer currentPage;
    private Integer totalPage;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "memberId")
    @JsonIgnore
    private Member member;


}