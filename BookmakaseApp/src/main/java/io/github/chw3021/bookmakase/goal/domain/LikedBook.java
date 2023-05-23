package io.github.chw3021.bookmakase.goal.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class LikedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "itemId")
    private Book book;
    
    
    @ManyToOne
    @JoinColumn(name = "memberId")
    @JsonIgnore
    private Member member;


}