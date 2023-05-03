package io.github.chw3021.bookmakase.journal.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
public class UpdateJournalRequest {

    @NonNull
    private LocalDate date;

    @NonNull
    private String content;

    // Getters and Setters
}