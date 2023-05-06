package io.github.chw3021.bookmakase.signservice.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserRequest{

    private Long Id;

    private String account;

    private String password;

    private int prefer;//선호 카테고리의 ID를 저장

    private String Email;

}