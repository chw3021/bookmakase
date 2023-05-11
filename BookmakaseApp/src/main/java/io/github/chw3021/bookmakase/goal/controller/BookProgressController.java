package io.github.chw3021.bookmakase.goal.controller;

import io.github.chw3021.bookmakase.goal.domain.BookProgress;
import io.github.chw3021.bookmakase.goal.service.BookProgressService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookprogress")
public class BookProgressController {

    private final BookProgressService bookProgressService;

    @PostMapping
    public BookProgress save(@RequestBody BookProgress bookProgress) {
        return bookProgressService.save(bookProgress);
    }

}
