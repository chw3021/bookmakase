package io.github.chw3021.bookmakase.signservice.service;

import java.util.Collections;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.chw3021.bookmakase.signservice.domain.Authority;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.domain.dto.SignRequest;
import io.github.chw3021.bookmakase.signservice.domain.dto.SignResponse;
import io.github.chw3021.bookmakase.signservice.domain.dto.UserRequest;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;
import io.github.chw3021.bookmakase.signservice.security.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
 //사용자가 이용하는 서비스(로그인,로그아웃 등 의 메소드)
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtProvider jwtProvider;

    public SignResponse login(SignRequest request) throws Exception {
        Member member = memberRepository.findByAccount(request.getAccount()).orElseThrow(() ->
                new BadCredentialsException("잘못된 계정정보입니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("잘못된 비밀번호입니다.");
        }

        return SignResponse.builder()
                .id(member.getId())
                .account(member.getAccount())
                .name(member.getName())
                .Email(member.getEmail())
                .age(member.getAge())
                .gender(member.getGender())
                .prefer(member.getPrefer())
                .Admin_check(member.getAdmin_check())
                .build();

    }

    public boolean register(SignRequest request) throws Exception {
        try {
            Member member = Member.builder()
            		.id(request.getId())
                    .account(request.getAccount())
                    .password(passwordEncoder.encode(request.getPassword())) 
                    .name(request.getName())
                    .email(request.getEmail())
                    .age(request.getAge())
                    .gender(request.getGender())
                    .prefer(request.getPrefer())
                    .build();

            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_USER").build()));

            memberRepository.save(member);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
        return true;
    }
    


    public boolean createAdminMember(SignRequest request) throws Exception {
        try {
            Member member = Member.builder()
            		.id(request.getId())
                    .account(request.getAccount())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .email(request.getEmail())
                    .age(request.getAge())
                    .gender(request.getGender())
                    .Admin_check(1)
                    .prefer(request.getPrefer())
                    .build();

            member.setRoles(Collections.singletonList(Authority.builder().name("ROLE_ADMIN").build()));

            memberRepository.save(member);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("잘못된 요청입니다.");
        }
        return true;
    }
    

    public SignResponse getMember(String account) throws Exception {
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new Exception("계정을 찾을 수 없습니다."));
        return new SignResponse(member);
    }


    public boolean info_change(Long Id, UserRequest request) throws Exception {
        Member member = memberRepository.findById(Id).orElseThrow(()->
                new IllegalArgumentException("수정에 실패하였습니다"));
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setEmail(request.getEmail());
        member.setPrefer(request.getPrefer());

        memberRepository.save(member);


        return true;
    }
    public String FindAccount(UserRequest request) throws Exception {
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new IllegalArgumentException("이메일을 찾을수 없습니다"));

        return member.getAccount();
    }


}