package io.github.chw3021.bookmakase.journal.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UpdateJournalRequest {

    private Long id;
    private String content;
    private String title;

    // Getters and Setters
}