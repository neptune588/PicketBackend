package com.swyg.picketbackend.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @Schema(description = "로그인 이메일",example = "test@naver.com")
    String email;

    @Schema(description = "로그인 비밀번호",example = "password")
    String password;

        
    // email,password 을 기반으로 한 권한 추출
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
