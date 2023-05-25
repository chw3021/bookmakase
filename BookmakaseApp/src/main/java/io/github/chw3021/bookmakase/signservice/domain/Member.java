package io.github.chw3021.bookmakase.signservice.domain;


import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Builder @AllArgsConstructor @NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memberId")
    private Long id;

    @Column(unique = true)
    private String account;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private int prefer;//선호 카테고리의 ID를 저장

    @Column(unique = true)
    private String email;

    @Column
    private int age;

    @Column
    private char gender;//m는 남성, f는 여성

    @Column
    private int warned;

    @Column @Nullable
    private LocalDateTime ban;
    
    //이후로 getter setter

    public LocalDateTime getBan() {return ban;}
    public void setBan(LocalDateTime ban) {
        this.ban = ban;
        }

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    @Builder.Default
    @ElementCollection(targetClass=Authority.class)
    private List<Authority> roles = new ArrayList<>();

    public void setRoles(List<Authority> role) {
        this.roles = role;
        role.forEach(o -> o.setMember(this));
    }
    public List<Authority> getRoles() {
    	return roles;
    }


}