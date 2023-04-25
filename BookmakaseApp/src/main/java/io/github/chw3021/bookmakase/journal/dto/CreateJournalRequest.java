package io.github.chw3021.bookmakase.journal.dto;

import java.time.LocalDate;

import io.github.chw3021.bookmakase.bookdata.dto.Book;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
public class CreateJournalRequest {

	@NonNull
    private Member member;

	@NonNull
    private Book book;

	@NonNull
    private LocalDate date;

	@NonNull
    private String content;

}