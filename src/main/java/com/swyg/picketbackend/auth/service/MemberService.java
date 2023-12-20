package com.swyg.picketbackend.auth.service;


import com.swyg.picketbackend.auth.domain.Member;
import com.swyg.picketbackend.auth.dto.member.PutPasswordDTO;
import com.swyg.picketbackend.auth.repository.MemberRepository;
import com.swyg.picketbackend.global.exception.CustomException;
import com.swyg.picketbackend.global.util.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    @Transactional
    public void passwordModify(PutPasswordDTO putPasswordDTO) {

        String email = putPasswordDTO.getEmail();

        // 메일에 해당하는 member get 존재하지 않으면 elseThrow
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOT_FOUND));

        String newPassword = generatePassword();

        log.info(newPassword);

        String encNewPassword = passwordEncoder.encode(newPassword);

        member.modifyPassword(encNewPassword);

        memberRepository.save(member);

        // 비밀번호 메일 전송 로직
        MimeMessage mimeMessage = createMessage(email,newPassword);
        log.info("Mail 전송 시작");
        javaMailSender.send(mimeMessage);
        log.info("Mail 전송 완료");

    }


    public String generatePassword() { // 비밀번호 생성 메서드

        StringBuilder password = new StringBuilder();

        while (password.length() < 8 || password.length() > 15) {
            char randomChar = getRandomChar();
            password.append(randomChar);
        }

        return password.toString();
    }


    public char getRandomChar() { // 랜덤 문자 생성 메서드

        SecureRandom random = new SecureRandom();
        int charType = random.nextInt(2);

        return switch (charType) {
            case 0 ->
                // 숫자를 무작위로 선택하여 '0'부터 '9'까지의 문자로 변환
                    (char) ('0' + random.nextInt(10));
            case 1 ->
                /// 소문자 알파벳을 무작위로 선택하여 'a'부터 'z'까지의 문자로 변환
                    (char) ('a' + random.nextInt(26));
            default ->
                // 이 부분은 예상치 못한 상황이 발생했을 때의 예외 처리
                    throw new IllegalStateException("Unexpected value: " + charType);
        };
    }

    public MimeMessage createMessage(String email, String newPassword) {  // 비밀번호 전송 메일 메서드
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setFrom("picket@domain.com");  // TODO : 사이트 도메인으로 추후 수정할 것
            messageHelper.setTo(email);
            messageHelper.setSubject("[Picket] 비밀번호 변경 안내");

            String body = "<html><body style='background-color: #000000 !important; margin: 0 auto; max-width: 600px; word-break: break-all; padding-top: 50px; color: #ffffff;'>";
            body += "<h1 style='padding-top: 50px; font-size: 30px;'>비밀번호 변경 안내</h1>";
            body += "<p style='padding-top: 20px; font-size: 18px; opacity: 0.6; line-height: 30px; font-weight: 400;'>안녕하세요? Bucket 관리자입니다.<br />";
            body += "계정의 새로운 비밀번호가 설정되었습니다.<br />";
            body += "하단의 새로운 비밀번호로 로그인 해주세요.<br />";
            body += "항상 최선의 노력을 다하는 Picket이 되겠습니다.<br />";
            body += "감사합니다.</p>";
            body += "<div class='code-box' style='margin-top: 50px; padding-top: 20px; color: #000000; padding-bottom: 20px; font-size: 25px; text-align: center; background-color: #f4f4f4; border-radius: 10px;'>" + newPassword + "</div>";
            body += "</body></html>";

            messageHelper.setText(body, true);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return mimeMessage;
    }




}
