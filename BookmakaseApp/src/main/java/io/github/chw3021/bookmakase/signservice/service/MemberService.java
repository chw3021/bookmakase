package io.github.chw3021.bookmakase.signservice.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import io.github.chw3021.bookmakase.signservice.controller.SignException;
import io.github.chw3021.bookmakase.signservice.domain.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.chw3021.bookmakase.signservice.domain.Authority;
import io.github.chw3021.bookmakase.signservice.domain.Member;
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

    public SignResponse login(SignRequest request) {
        Member member = memberRepository.findByAccount(request.getAccount()).orElseThrow(() ->
                new SignException("잘못된 계정 정보입니다."));
        LocalDateTime now = LocalDateTime.now();

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new SignException("잘못된 비밀번호입니다.");
        }
        if (member.getBan() != null && now.isBefore(member.getBan())) {
            throw new SignException(member.getBan() + " 까지 계정이 정지되었습니다.");
        }

        // 정상 로그인 처리
        return SignResponse.builder()
                .id(member.getId())
                .account(member.getAccount())
                .name(member.getName())
                .Email(member.getEmail())
                .age(member.getAge())
                .gender(member.getGender())
                .prefer(member.getPrefer())
                .roles(member.getRoles())
                .token(jwtProvider.createToken(member.getAccount(), member.getRoles()))
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
                    .warned(0)
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
    

    public SignResponse getMember(SignRequest request){
        Member member = memberRepository.findByAccount(request.getAccount()).orElseThrow(() ->
                new SignException("계정을 찾을 수 없습니다."));
        return new SignResponse(member);
    }


    public boolean info_change(UserRequest request){
        Member member = memberRepository.findById(request.getId()).orElseThrow(() ->
                new SignException("해당 계정을 찾을 수 없습니다."));
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setEmail(request.getEmail());
        member.setPrefer(request.getPrefer());

        memberRepository.save(member);

        return true;
    }
    public String FindAccount(UserRequest request){
        Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new SignException("이메일을 찾을수 없습니다"));

        return member.getAccount();
    }

    public Boolean withdrawal(UserRequest request) throws Exception {
        Member member = memberRepository.findByAccount(request.getAccount()).orElseThrow(() ->
                new Exception("계정을 찾을 수 없습니다."));
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            return false;
        }
        memberRepository.deleteById(member.getId());

        return true;
    }
    
    public Boolean accountWarn(Long id, Integer warn) throws Exception {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new Exception("계정을 찾을 수 없습니다."));

        member.setWarned(warn);
        memberRepository.save(member);
        return true;
    }

    
    public Boolean accountBan(BanDto request) throws Exception {
        Member member = memberRepository.findById(request.getId()).orElseThrow(() ->
                new Exception("계정을 찾을 수 없습니다."));

        LocalDateTime banned = LocalDateTime.now().plusDays(request.getBantime());
        member.setBan(banned);
        memberRepository.save(member);
        return true;
    }

    public List<Member> getMemberList() {

        return memberRepository.findAll();
    }





}