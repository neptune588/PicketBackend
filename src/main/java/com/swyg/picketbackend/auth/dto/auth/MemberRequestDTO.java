package com.swyg.picketbackend.auth.dto.auth;


import com.swyg.picketbackend.auth.domain.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDTO {

    @NotBlank(message = "이메일을 입력해 주세요.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    @Schema(description = "가입 이메일",example = "test@naver.com")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,15}$", message = "8~15자리의 숫자와 영문자가 각각 하나씩 포함되어야 합니다.")
    @Schema(description = "가입 비밀번호",example = "password")
    private String password;

    @Schema(description = "가입 닉네임",example = "nickname")
    private String nickname;

    private String imageUrl;

    // dto -> Entity
    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.ROLE_USER)
                .nickname(nickname)
                .imageUrl(imageUrl)
                .providerId("none")
                .socialType(SocialType.GENERAL)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
