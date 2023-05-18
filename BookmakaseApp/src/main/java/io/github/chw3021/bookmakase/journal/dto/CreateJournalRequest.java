package io.github.chw3021.bookmakase.journal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateJournalRequest {

    private Long memberId;

    private Long itemId;

    private String title;

    private String content;
    
    private String image;

}