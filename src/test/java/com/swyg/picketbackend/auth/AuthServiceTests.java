package com.swyg.picketbackend.auth;


import com.swyg.picketbackend.auth.dto.auth.req.MemberRequestDTO;
import com.swyg.picketbackend.auth.dto.auth.res.SignupResponseDTO;
import com.swyg.picketbackend.auth.service.AuthService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
public class AuthServiceTests {


    @Autowired
    private AuthService authService;

    @Test
    @DisplayName("회원 가입 서비스 테스트")
    public void signupTests() {
        // given
        MemberRequestDTO memberRequestDTO = MemberRequestDTO.builder()
                .email("jiwoong423@naver.com")
                .password("password")
                .build();

        // when
        SignupResponseDTO signupResponseDTO = authService.signup(memberRequestDTO);

        // then
        log.info(signupResponseDTO.getEmail());

    }

    @Test
    @DisplayName("회원 가입 닉네임 설정 서비스 테스트")
    public void nickNameAddTests() {
        // given
        String email = "test1@naver.com";
        String nickname = "wooni";
        // when
        authService.nickNameAdd(email,nickname);
        // then

    }
}
