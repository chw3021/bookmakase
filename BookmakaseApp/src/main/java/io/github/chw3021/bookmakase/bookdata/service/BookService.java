package io.github.chw3021.bookmakase.bookdata.service;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {

    @Autowired
    private final BookRepository bookRepository;
    public boolean isBookExist(Long itemId){
        return bookRepository.findById(itemId).isPresent();
    }
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}