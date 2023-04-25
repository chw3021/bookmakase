package io.github.chw3021.bookmakase.goal.domain;

import java.util.ArrayList;
import java.util.List;

import io.github.chw3021.bookmakase.bookdata.dto.Book;
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

    @OneToMany(mappedBy = "bookShelf", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> wantToRead = new ArrayList<>();

    @OneToMany(mappedBy = "bookShelf", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookProgress> currentlyReading = new ArrayList<>();

    @OneToMany(mappedBy = "bookShelf", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> finished = new ArrayList<>();

    public BookShelf() {
    }

    public BookShelf(GoalUser goaluser) {
        this.goaluser = goaluser;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GoalUser getGoalUser() {
    	return goaluser;
    }

    public void setGoalUser(GoalUser goaluser) {
        this.goaluser = goaluser;
    }

    public List<Book> getWantToRead() {
        return wantToRead;
    }

    public void setWantToRead(List<Book> wantToRead) {
        this.wantToRead = wantToRead;
    }

    public void addWantToRead(Book book) {
        book.setBookShelf(this);
        wantToRead.add(book);
    }

    public void removeWantToRead(Book book) {
        wantToRead.remove(book);
    }

    public List<BookProgress> getCurrentlyReading() {
        return currentlyReading;
    }

    public void setCurrentlyReading(List<BookProgress> currentlyReading) {
        this.currentlyReading = currentlyReading;
    }

    public void addCurrentlyReading(BookProgress progress) {
        progress.setBookShelf(this);
        currentlyReading.add(progress);
    }

    public void removeCurrentlyReading(BookProgress progress) {
        currentlyReading.remove(progress);
    }

    public List<Book> getFinished() {
        return finished;
    }

    public void setFinished(List<Book> finished) {
        this.finished = finished;
    }

    public void addFinished(Book book) {
        book.setBookShelf(this);
        finished.add(book);
    }

    public void removeFinished(Book book) {
        finished.remove(book);
    }
}