package io.github.chw3021.bookmakase.goal.domain;

import java.util.ArrayList;
import java.util.List;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Getter @Setter
@Builder @AllArgsConstructor @NoArgsConstructor
public class BookShelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "bookId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> wantToRead = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "bookProgress", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookProgress> currentlyReading = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "bookId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> finished = new ArrayList<>();
/*
    public BookShelf(Member member) {
        this.member = member;
    }*/

    // Getters and setters


    public void addWantToRead(Book book) {
    	if(wantToRead.contains(book)){
    		return;
    	}
        wantToRead.add(book);
    }

    public void removeWantToRead(Book book) {
    	if(!wantToRead.contains(book)){
    		return;
    	}
        wantToRead.remove(book);
    }


    public void addCurrentlyReading(BookProgress progress) {
    	if(!currentlyReading.contains(progress)){
    		return;
    	}
        progress.setBookShelf(this);
        currentlyReading.add(progress);
    }

    public void removeCurrentlyReading(BookProgress progress) {
    	if(currentlyReading.contains(progress)){
    		return;
    	}
        currentlyReading.remove(progress);
    }


    public void addFinished(Book book) {
    	if(finished.contains(book)){
    		return;
    	}
        finished.add(book);
    }

    public void removeFinished(Book book) {
    	if(!finished.contains(book)){
    		return;
    	}
        finished.remove(book);
    }

    public void addBookProgress(BookProgress bookProgress) {
        bookProgress.setBookShelf(this);
        currentlyReading.add(bookProgress);
    }

    public void removeBookProgressById(Long bookProgressId) {
        currentlyReading.removeIf(progress -> progress.getId().equals(bookProgressId));
    }
    
}