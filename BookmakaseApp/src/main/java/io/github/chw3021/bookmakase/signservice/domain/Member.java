package io.github.chw3021.bookmakase.signservice.domain;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.github.chw3021.bookmakase.bookdata.domain.Book;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
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

    @Column(nullable = false)
    private String email;

    @Column
    private int age;

    @Column
    private char gender;//m는 남성, f는 여성


    @Column @Nullable
    private LocalDateTime ban;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_like",
            joinColumns = @JoinColumn(name = "memberId"),
            inverseJoinColumns = @JoinColumn(name = "itemId"))
    private List<Book> likedBooks = new ArrayList<>();

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
    	return email;
    }
    public void setEmail(String Email) {
        this.email = Email;
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

    public LocalDateTime getBan() {return ban;}
    public void setBan(LocalDateTime ban) {
        this.ban = ban;}

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