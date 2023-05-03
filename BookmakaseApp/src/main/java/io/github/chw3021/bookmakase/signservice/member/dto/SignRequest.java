package io.github.chw3021.bookmakase.signservice.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignRequest{

    private Long id;

    private String account;

    private String password;

    private String name;

    private int prefer;//선호 카테고리의 ID를 저장

    private Long phoneNumber;

    private int age;

    private char gender;//m는 남성, f는 여성

}