package com.swyg.picketbackend.auth.dto.auth.req;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NicknameRequestDTO {

    
    @Schema(description = "가입 이메일",example = "test@naver.com")
    private String email;
    
    @Schema(description = "설정하고자 하는 닉네임",example = "nickname")
    private String nickname;
}
