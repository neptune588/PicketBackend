package com.swyg.picketbackend.auth.controller;

import com.swyg.picketbackend.auth.dto.*;
import com.swyg.picketbackend.auth.service.AuthService;
import com.swyg.picketbackend.global.dto.SuccessResponse;
import com.swyg.picketbackend.global.exception.CustomException;
import com.swyg.picketbackend.global.util.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AuthController",description = "인증 관련 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @Operation(summary = "회원가입", description = "회원 가입 API")
    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse> signup(@RequestBody MemberRequestDTO memberRequestDto) throws CustomException {
        authService.signup(memberRequestDto);
        return SuccessResponse.success(SuccessCode.INSERT_SUCCESS);
    }

    @Operation(summary = "로그인", description = "로그인을 통해 인증을 위한 엑세스 토큰 및 리프레쉬 토큰을 획득한다.(body로 리턴)")
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(authService.login(loginDTO));
    }

    @Operation(summary = "토큰 재발급", description = "토큰이 만료되거나 유효하지 않으면 재발급한다.(body로 리턴)")
    @PostMapping("/reissue")
    public ResponseEntity<TokenDTO> reissue(@RequestBody TokenRequestDTO tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    
}

