package io.github.chw3021.bookmakase.signservice.domain;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.github.chw3021.bookmakase.review.domain.Review;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(nullable = false)
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

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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