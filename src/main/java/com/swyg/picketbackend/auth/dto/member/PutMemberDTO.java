package com.swyg.picketbackend.auth.dto.member;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PutMemberDTO {

    @Schema(description = "프로필 이미지 url",example = "Amazon s3 버킷 주소")
    private String imageUrl;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,15}$", message = "8~15자리의 숫자와 영문자가 각각 하나씩 포함되어야 합니다.")
    @Schema(description = "가입 비밀번호",example = "password")
    private String password;

    @Schema(description = "가입 닉네임",example = "nickname")
    private String nickname;




}
