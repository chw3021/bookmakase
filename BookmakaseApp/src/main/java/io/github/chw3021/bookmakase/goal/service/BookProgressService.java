package io.github.chw3021.bookmakase.goal.service;

import io.github.chw3021.bookmakase.goal.domain.BookProgress;
import io.github.chw3021.bookmakase.goal.repository.BookProgressRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookProgressService{

    @Autowired
    private final BookProgressRepository bookProgressRepository;

    public BookProgress save(BookProgress bookProgress) {
        return bookProgressRepository.save(bookProgress);
    }

    public void delete(BookProgress bookProgress) {
        bookProgressRepository.delete(bookProgress);
    }

    public BookProgress update(BookProgress bookProgress) {
        return bookProgressRepository.save(bookProgress);
    }

}
