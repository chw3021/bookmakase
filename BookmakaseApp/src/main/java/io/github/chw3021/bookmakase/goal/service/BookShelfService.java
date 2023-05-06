package io.github.chw3021.bookmakase.goal.service;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.repository.BookRepository;
import io.github.chw3021.bookmakase.goal.domain.BookProgress;
import io.github.chw3021.bookmakase.goal.domain.BookShelf;
import io.github.chw3021.bookmakase.goal.repository.BookShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookShelfService {

    private final BookShelfRepository bookShelfRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookShelfService(BookShelfRepository bookShelfRepository, BookRepository bookRepository) {
        this.bookShelfRepository = bookShelfRepository;
        this.bookRepository = bookRepository;
    }

    public BookShelf createBookShelf() {
        BookShelf bookShelf = new BookShelf();
        return bookShelfRepository.save(bookShelf);
    }

    public List<BookShelf> getAllBookShelves() {
        return bookShelfRepository.findAll();
    }

    public BookShelf getBookShelfById(Long id) {
        Optional<BookShelf> optionalBookShelf = bookShelfRepository.findById(id);
        if (optionalBookShelf.isPresent()) {
            return optionalBookShelf.get();
        }
		return null;
    }

    public BookShelf addBookToShelf(Long shelfId, Long bookId) {
        BookShelf bookShelf = getBookShelfById(shelfId);
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Invalid book id: " + bookId));
        
        BookProgress bookProgress = new BookProgress(book, bookShelf);

        bookShelf.addBookProgress(bookProgress);
        bookShelfRepository.save(bookShelf);

        return bookShelf;
    }

    public void removeBookFromShelf(Long shelfId, Long bookProgressId) {
        BookShelf bookShelf = getBookShelfById(shelfId);
        bookShelf.removeBookProgressById(bookProgressId);
        bookShelfRepository.save(bookShelf);
    }
    
    public BookShelf updateBookShelf(Long id, BookShelf us) {
        BookShelf bookShelf = getBookShelfById(id);

        if (bookShelf != null) {
            bookShelf.setCurrentlyReading(us.getCurrentlyReading());
            bookShelf.setFinished(us.getFinished());
            bookShelf.setWantToRead(us.getWantToRead());
            return bookShelfRepository.save(bookShelf);
        } else {
            throw new IllegalArgumentException("Invalid bookshelf id: " + id);
        }
    }

    public void deleteBookShelf(Long id) {
        BookShelf bookShelf = getBookShelfById(id);

        if (bookShelf != null) {
            bookShelfRepository.delete(bookShelf);
        } else {
            throw new IllegalArgumentException("Invalid bookshelf id: " + id);
        }
    }
}
