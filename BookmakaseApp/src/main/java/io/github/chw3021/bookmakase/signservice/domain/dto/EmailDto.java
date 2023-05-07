package io.github.chw3021.bookmakase.signservice.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter @Setter @NoArgsConstructor
public class EmailDto {

    private String address;
    private String title;
    private String message;
}
