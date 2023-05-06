package io.github.chw3021.bookmakase.goal.domain;

import java.time.LocalDateTime;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class BookProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private GoalUser goaluser;
    
    private Integer currentPage;
    private Integer totalPage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookshelf_id")
    private BookShelf bookShelf;
    
    public BookProgress(Book book, BookShelf bookShelf) {
        this.book = book;
        this.bookShelf = bookShelf;
    }
    
    @Builder
    public BookProgress(GoalUser goaluser, Book book, Integer currentPage, Integer totalPage, LocalDateTime startDate, LocalDateTime endDate) {
        this.goaluser = goaluser;
        this.book = book;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and setters

}
