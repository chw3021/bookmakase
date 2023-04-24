package io.github.chw3021.bookmakase.signservice.member.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignRequest{

    private Long id;

    private String account;

    private String password;

    private String name;

    private int prefer;//선호 카테고리의 ID를 저장

    @Column(unique = true)
    private Long phonenumber;

    private int age;

    private char gender;//m는 남성, f는 여성

}