package com.swyg.picketbackend.auth.dto;


import com.swyg.picketbackend.auth.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDTO {

    @Schema(description = "가입 이메일",example = "test@naver.com")
    private String email;

    @Schema(description = "가입 비밀번호",example = "password")
    private String password;

    @Schema(description = "가입 닉네임",example = "nickname")
    private String nickname;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .nickname(nickname)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
