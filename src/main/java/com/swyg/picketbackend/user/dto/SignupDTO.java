package com.swyg.picketbackend.user.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignupDTO {

    @NotBlank(message = "이메일은 입력해 주세요")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;
    
    @NotBlank(message = "비밀번호를 입력해 주세요")
    @Pattern(regexp ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")   // 최소 8자, 하나 이상의 문자,하나의 숫자 및 하나의 특수 문자 정규식

    private String password;

    @NotBlank(message = "닉네임을 입력해 주세요")
    private String nickname;



}
