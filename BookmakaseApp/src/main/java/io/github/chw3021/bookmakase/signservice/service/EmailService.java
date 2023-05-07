package io.github.chw3021.bookmakase.signservice.service;

import io.github.chw3021.bookmakase.signservice.domain.Member;
import io.github.chw3021.bookmakase.signservice.domain.dto.EmailDto;
import io.github.chw3021.bookmakase.signservice.domain.dto.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.github.chw3021.bookmakase.signservice.repository.MemberRepository;

import java.util.Random;


@Service
@AllArgsConstructor
public class EmailService {
    @Autowired
    private final MemberRepository memberRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    private JavaMailSender emailSender;
    private static final String hostSMTPid = "naxc14@gmail.com";

    public String createTempPwd() {
        int from = 48; // 0
        int to = 122; // z
        int targetStringLength = 10;
        String pwd;
        Random random = new Random();
        pwd = random.ints(from, to + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return pwd;
    }

    public String tempPwd(UserRequest request) throws Exception {
        Member member = memberRepository.findByEmailAndAccount(request.getEmail(),request.getAccount()).orElseThrow(() ->
                new IllegalArgumentException("이메일또는 계정을 찾을수 없습니다."));
        String tempPwd = createTempPwd();
        member.setPassword(passwordEncoder.encode(tempPwd));
        Member.builder()
                .password(passwordEncoder.encode(member.getPassword()))
                .build();
        return tempPwd;
    }

    public Boolean sendPwdEmail(UserRequest request) throws Exception {

        String tempPwd = tempPwd(request);
        EmailDto email = new EmailDto();
        email.setAddress(request.getEmail());
        email.setTitle(request.getAccount()+"님의 BOOKMAKASE 임시비밀번호 안내입니다.");
        email.setMessage("임시 비밀번호는 "+ tempPwd +" 입니다.");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getAddress());
        message.setFrom(hostSMTPid);
        message.setSubject(email.getTitle());
        message.setText(email.getMessage());

        emailSender.send(message);

        return true;
    }

}
