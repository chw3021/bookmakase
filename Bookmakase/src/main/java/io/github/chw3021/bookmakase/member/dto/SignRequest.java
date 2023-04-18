package io.github.chw3021.bookmakase.member.dto;

import io.github.chw3021.bookmakase.member.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignRequest extends Member{

    private Long id;

    private String account;

    private String password;

    private String nickname;

    private String name;

    private String email;
    

}