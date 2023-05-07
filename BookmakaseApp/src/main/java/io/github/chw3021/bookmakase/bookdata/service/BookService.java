package io.github.chw3021.bookmakase.bookdata.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import io.github.chw3021.bookmakase.bookdata.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BookService {
	
	@Autowired
    private final BookRepository bookRepository;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}