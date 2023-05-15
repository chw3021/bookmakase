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

    @PutMapping("/MyPage/info_change")
    public ResponseEntity<Boolean> info_change(@RequestBody UserRequest request) throws Exception {
        return new ResponseEntity<>(memberService.info_change(request), HttpStatus.OK);
    }
    @PostMapping("/Find/Account")
    public ResponseEntity<String> findid(@RequestBody UserRequest request)throws Exception {
        return new ResponseEntity<>(memberService.FindAccount(request), HttpStatus.OK);
    }

    @PostMapping("/Find/PWD")
    public ResponseEntity<Boolean> Findpwd(@RequestBody UserRequest request)throws Exception {
        return new ResponseEntity<>(emailService.sendPwdEmail(request), HttpStatus.OK);
    }
    @PostMapping("/withdrawMember")
    public ResponseEntity<Boolean> deleteMember(@RequestBody UserRequest request) throws Exception {
        return new ResponseEntity<>(memberService.withdrawal(request), HttpStatus.OK);
    }
    @PostMapping("/admin/warnMember")
    public ResponseEntity<Boolean> warnMember(@RequestParam Long userId, @RequestParam Integer warn) throws Exception {
        return new ResponseEntity<>(memberService.accountWarn(userId, warn), HttpStatus.OK);
    }
    @PostMapping("/admin/banMember")
    public ResponseEntity<Boolean> memberban(@RequestBody BanDto request) throws Exception {
        return new ResponseEntity<>(memberService.accountBan(request), HttpStatus.OK);
    }
    @GetMapping("/getMemberList")
    public ResponseEntity<List<Member>> getMembers() throws Exception {
        return new ResponseEntity<>(memberService.getMemberList(), HttpStatus.OK);
    }
    //1:경고 횟수 추가(3회시 15일정지, 5회시 30일 정지), 2: 영구정지
    @PostMapping("/admin/reportProcess")
    public ResponseEntity<Report> reportProcess(@RequestParam Long reportId, @RequestParam Integer process) throws Exception {
    	return new ResponseEntity<>(reviewService.processReport(reportId, process), HttpStatus.OK);
    }

}