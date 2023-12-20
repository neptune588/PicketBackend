package com.swyg.picketbackend.auth.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInDTO {

    @Schema(description = "로그인 이메일",example = "test@naver.com")
    String email;

    @Schema(description = "로그인 비밀번호",example = "password")
    String password;


}
