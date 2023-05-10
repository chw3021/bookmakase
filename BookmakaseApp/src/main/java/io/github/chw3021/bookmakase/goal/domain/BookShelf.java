package io.github.chw3021.bookmakase.goal.domain;

import java.util.ArrayList;
import java.util.List;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class BookShelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private GoalUser goaluser;

    @OneToMany(mappedBy = "itemId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> wantToRead = new ArrayList<>();

    @OneToMany(mappedBy = "bookShelf", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookProgress> currentlyReading = new ArrayList<>();

    @OneToMany(mappedBy = "itemId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> finished = new ArrayList<>();

    public BookShelf() {
    }

    public BookShelf(GoalUser goaluser) {
        this.goaluser = goaluser;
    }

    // Getters and setters


    public void addWantToRead(Book book) {
        wantToRead.add(book);
    }

    public void removeWantToRead(Book book) {
        wantToRead.remove(book);
    }


    public void addCurrentlyReading(BookProgress progress) {
        progress.setBookShelf(this);
        currentlyReading.add(progress);
    }

    public void removeCurrentlyReading(BookProgress progress) {
        currentlyReading.remove(progress);
    }


    public void addFinished(Book book) {
        finished.add(book);
    }

    public void removeFinished(Book book) {
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