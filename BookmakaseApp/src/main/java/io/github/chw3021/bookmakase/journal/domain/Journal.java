package io.github.chw3021.bookmakase.journal.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Journal {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "memberId")
    @JsonIgnore
    private Member member;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private Book book;

    private LocalDate date;

    private String title;

    private String content;
    
    private String image;
}