package io.github.chw3021.bookmakase.signservice.domain;


import java.util.List;

import io.github.chw3021.bookmakase.signservice.member.Authority;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder @AllArgsConstructor @NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(unique = true)
    private String account; //account는 무엇? ID, Nickname 있으니 필요해 보이지는 않음.

    private String password;

    private String nickname;

    private String name;

    @Column(unique = true)
    private String email;

    //이후로 getter setter
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
   
    public String getEmail() {
    	return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAccount() {
    	return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();

    public void setRoles(List<Authority> role) {
        this.roles = role;
        role.forEach(o -> o.setMember(this));
    }
    public List<Authority> getRoles() {
    	return roles;
    }
    

}