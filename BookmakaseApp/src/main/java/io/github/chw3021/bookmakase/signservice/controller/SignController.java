package io.github.chw3021.bookmakase.signservice.controller;

import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.member.dto.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import io.github.chw3021.bookmakase.signservice.SignService;
import io.github.chw3021.bookmakase.signservice.member.dto.SignRequest;
import io.github.chw3021.bookmakase.signservice.member.dto.SignResponse;
import lombok.RequiredArgsConstructor;

@RestController
@Controller
@RequiredArgsConstructor
public class SignController {

    private final SignService memberService;

    @PostMapping(value = "/login")
    public ResponseEntity<SignResponse> signin(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(memberService.login(request), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> signup(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(memberService.register(request), HttpStatus.OK);
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
}