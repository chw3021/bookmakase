package io.github.chw3021.bookmakase.journal.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateJournalRequest {

    private Long memberId;

    private Long itemId;

    private String content;
    
    private String image;

}