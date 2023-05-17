package io.github.chw3021.bookmakase.signservice.controller;

import io.github.chw3021.bookmakase.review.domain.Report;
import io.github.chw3021.bookmakase.review.service.ReviewService;
import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.domain.dto.BanDto;
import io.github.chw3021.bookmakase.signservice.domain.dto.SignRequest;
import io.github.chw3021.bookmakase.signservice.domain.dto.SignResponse;
import io.github.chw3021.bookmakase.signservice.domain.dto.UserRequest;
import io.github.chw3021.bookmakase.signservice.service.EmailService;
import io.github.chw3021.bookmakase.signservice.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@Controller
@RequiredArgsConstructor
public class MemberController {
	@Autowired
    private final MemberService memberService;
	@Autowired
    private final EmailService emailService;
    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = "/login")
    public ResponseEntity<?> signin(@RequestBody SignRequest request) {
        try {
            SignResponse response = memberService.login(request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (SignException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        } catch (Exception e) {
            String errorMessage = "로그인 중에 오류가 발생했습니다.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Boolean> signup(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(memberService.register(request), HttpStatus.OK);
    }
    @PostMapping(value = "/admin")
    public ResponseEntity<Boolean> createAdminMember(@RequestBody SignRequest request) throws Exception {
        return new ResponseEntity<>(memberService.createAdminMember(request), HttpStatus.OK);
    }

    @PostMapping("/user/getInfo")
    public ResponseEntity<?> getUser(@RequestBody SignRequest request){
        try{
            return new ResponseEntity<>(memberService.getMember(request), HttpStatus.OK);
        }catch (SignException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }


    }

    /*
    @PostMapping("/admin/get")
    public ResponseEntity<?> getUserForAdmin(@RequestParam SignRequest request){
        try{
            return new ResponseEntity<>( memberService.getMember(request.getAccount()), HttpStatus.OK);
        }catch (SignException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
    }
    */

    @PutMapping("/MyPage/info_change")
    public ResponseEntity<?> info_change(@RequestBody UserRequest request){
        try{
            return new ResponseEntity<>(memberService.info_change(request), HttpStatus.OK);
        }catch (SignException e) {
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }

    }
    @PostMapping("/Find/Account")
    public ResponseEntity<?> findid(@RequestBody UserRequest request){
        try{
            return new ResponseEntity<>(memberService.FindAccount(request), HttpStatus.OK);
        }catch (SignException e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }

    }

    @PostMapping("/Find/PWD")
    public ResponseEntity<?> Findpwd(@RequestBody UserRequest request){
        try{
            return new ResponseEntity<>(emailService.sendPwdEmail(request), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
    }
    @PostMapping("/withdrawMember")
    public ResponseEntity<?> deleteMember(@RequestBody UserRequest request){
        try{
            return new ResponseEntity<>(memberService.withdrawal(request), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }

    }
    @PostMapping("/admin/warnMember")
    public ResponseEntity<Boolean> warnMember(@RequestParam Long userId, @RequestParam Integer warn) throws Exception {
        return new ResponseEntity<>(memberService.accountWarn(userId, warn), HttpStatus.OK);
    }
    @PostMapping("/admin/banMember")
    public ResponseEntity<?> memberban(@RequestBody BanDto request){
        try{
            return new ResponseEntity<>(memberService.accountBan(request), HttpStatus.OK);
        }catch (Exception e){
            String errorMessage = e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
        }
    }
    @GetMapping("/getMemberList")
    public ResponseEntity<List<Member>> getMembers(){
        return new ResponseEntity<>(memberService.getMemberList(), HttpStatus.OK);
    }


}