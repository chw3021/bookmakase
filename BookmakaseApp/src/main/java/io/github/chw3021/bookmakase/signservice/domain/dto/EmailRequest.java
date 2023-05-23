package io.github.chw3021.bookmakase.signservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmailRequest{

    private Long Id;

    private String account;

    private String Email;

    private String Message;

}