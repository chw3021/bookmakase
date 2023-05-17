package io.github.chw3021.bookmakase.journal.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
@Builder
public class UpdateJournalRequest {

    @NonNull
    private String content;

    // Getters and Setters
}