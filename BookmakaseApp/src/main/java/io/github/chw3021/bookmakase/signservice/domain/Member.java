package io.github.chw3021.bookmakase.signservice.domain;


import java.util.ArrayList;
import java.util.List;

import io.github.chw3021.bookmakase.signservice.member.Authority;
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

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Builder @AllArgsConstructor @NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String account;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private int prefer;//선호 카테고리의 ID를 저장

    @Column
    private String Email;

    @Column
    private int age;

    @Column
    private char gender;//m는 남성, f는 여성

    @Column
    private int Admin_check;



    //이후로 getter setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
   
    public String getEmail() {
    	return Email;
    }
    public void setEmail(String Email) {
        this.Email = Email;
    }
    
    public String getAccount() {
    	return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    
    
    public int getPrefer() {
    	return prefer;
    }
    public void setPrefer(Integer prefer) {
    	this.prefer = prefer;
    }
    
    
    public int getAge() {
    	return age;
    }
    public void setAge(Integer age) {
    	this.age = age;
    }
    
    public char getGender() {
    	return gender;
    }
    public void setGender(Character gender) {
    	this.gender = gender;
    }

    public int getAdmin_check() { return Admin_check; }
    public void setAdmin_check(int admin_check) {this.Admin_check = admin_check;}

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