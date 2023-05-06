package io.github.chw3021.bookmakase.signservice.domain.dto;


import java.util.List;
import java.util.ArrayList;

import io.github.chw3021.bookmakase.signservice.domain.Authority;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class SignResponse {

    private Long id;

    private String account;

    private String name;

    private String Email;

    private int prefer;

    private int age;

    private char gender;

    private List<Authority> roles = new ArrayList<>();

    private String token;

    private int Admin_check;

    public SignResponse(Member member) {
        this.id = member.getId();
        this.account = member.getAccount();
        this.name = member.getName();
        this.Email = member.getEmail();
        this.age = member.getAge();
        this.prefer = member.getPrefer();
        this.gender = member.getGender();
        this.roles = member.getRoles();
        this.Admin_check = member.getAdmin_check();
    }
}