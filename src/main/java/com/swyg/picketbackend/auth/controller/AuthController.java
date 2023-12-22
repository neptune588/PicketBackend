package com.swyg.picketbackend.auth.controller;

import com.swyg.picketbackend.auth.dto.auth.req.LoginDTO;
import com.swyg.picketbackend.auth.dto.auth.req.MemberRequestDTO;
import com.swyg.picketbackend.auth.dto.auth.req.NicknameRequestDTO;
import com.swyg.picketbackend.auth.dto.auth.res.SignupResponseDTO;
import com.swyg.picketbackend.auth.dto.auth.res.TokenResponseDTO;
import com.swyg.picketbackend.auth.dto.auth.req.TokenRequestDTO;
import com.swyg.picketbackend.auth.service.AuthService;
import com.swyg.picketbackend.global.dto.SuccessResponse;
import com.swyg.picketbackend.global.exception.CustomException;
import com.swyg.picketbackend.global.util.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AuthController", description = "인증 관련 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원 가입 API")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody MemberRequestDTO memberRequestDTO) throws CustomException {
        return ResponseEntity.ok(authService.signup(memberRequestDTO));
    }

    @Operation(summary = "회원가입 닉네임 설정", description = "회원 가입 닉네임 API")
    @PostMapping("/signup/nickname")
    public ResponseEntity<SuccessResponse> signupAddNickname(@RequestBody NicknameRequestDTO nicknameRequestDTO) throws CustomException {
        authService.nickNameAdd(nicknameRequestDTO);
        return SuccessResponse.success(SuccessCode.SIGNUP_SUCCESS);
    }

    @Operation(summary = "로그인", description = "로그인을 통해 인증을 위한 엑세스 토큰 및 리프레쉬 토큰을 획득한다.(response body로 리턴)")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @Operation(summary = "토큰 재발급", description = "토큰이 만료되거나 유효하지 않으면 재발급한다.(response body로 리턴)")
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDTO> reissue(@RequestBody TokenRequestDTO tokenRequestDto) throws CustomException {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }


}

