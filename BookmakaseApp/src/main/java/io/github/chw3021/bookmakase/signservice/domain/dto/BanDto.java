package io.github.chw3021.bookmakase.signservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class BanDto {
    private long id;
    private long bantime;
}
