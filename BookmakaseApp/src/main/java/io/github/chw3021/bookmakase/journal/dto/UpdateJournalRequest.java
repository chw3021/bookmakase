package io.github.chw3021.bookmakase.journal.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter @Setter
@Builder
public class UpdateJournalRequest {

    @NonNull
    private Long id;
    @NonNull
    private String content;

    // Getters and Setters
}