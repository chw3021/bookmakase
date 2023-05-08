package io.github.chw3021.bookmakase.signservice.controller;

import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.domain.dto.SignRequest;
import io.github.chw3021.bookmakase.signservice.domain.dto.SignResponse;
import io.github.chw3021.bookmakase.signservice.domain.dto.UserRequest;
import io.github.chw3021.bookmakase.signservice.service.EmailService;
import io.github.chw3021.bookmakase.signservice.service.MemberService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final EmailService emailService;

    @PostMapping(value = "/login")
    public ResponseEntity<SignResponse> signin(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> signup(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(memberService.register(request), HttpStatus.OK);
    }
    @PostMapping(value = "/admin")
    public ResponseEntity<Boolean> createAdminMember(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(memberService.createAdminMember(request), HttpStatus.OK);
    }

    @GetMapping("/user/get")
    public ResponseEntity<SignResponse> getUser(@RequestParam String account) throws Exception {
        return new ResponseEntity<>( memberService.getMember(account), HttpStatus.OK);
    }

    @GetMapping("/admin/get")
    public ResponseEntity<SignResponse> getUserForAdmin(@RequestParam String account) throws Exception {
        return new ResponseEntity<>( memberService.getMember(account), HttpStatus.OK);
    }

    @PutMapping("/MyPage/info_change/{id}")
    public ResponseEntity<Boolean> info_change(@PathVariable Long id, @RequestBody UserRequest request) throws Exception {
        return new ResponseEntity<>(memberService.info_change(id,request), HttpStatus.OK);
    }
    @PostMapping("/Find/Account")
    public ResponseEntity<String> findid(@RequestBody UserRequest request)throws Exception {
        return new ResponseEntity<>(memberService.FindAccount(request), HttpStatus.OK);
    }

    @PostMapping("/Find/PWD")
    public ResponseEntity<Boolean> Findpwd(@RequestBody UserRequest request)throws Exception {
        return new ResponseEntity<>(emailService.sendPwdEmail(request), HttpStatus.OK);
    }

}