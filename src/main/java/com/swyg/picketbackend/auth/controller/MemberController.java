package com.swyg.picketbackend.auth.controller;

import com.swyg.picketbackend.auth.dto.member.PutPasswordDTO;
import com.swyg.picketbackend.auth.service.MemberService;
import com.swyg.picketbackend.global.dto.SuccessResponse;
import com.swyg.picketbackend.global.exception.CustomException;
import com.swyg.picketbackend.global.util.SuccessCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "MemberController",description = "인증 관련 API")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "가입 이메일로 비밀번호 초기화", description = "비밀번호 찾기 API")
    @PostMapping("/reset-password")
    public ResponseEntity<SuccessResponse> modifyPassword(@RequestBody PutPasswordDTO putPasswordDTO) throws CustomException {
        memberService.passwordModify(putPasswordDTO);
        return SuccessResponse.success(SuccessCode.SIGNUP_SUCCESS);
    }

    @Operation(summary = "가입 이메일로 비밀번호 초기화", description = "비밀번호 찾기 API")
    @PostMapping("/reset-password")
    public ResponseEntity<SuccessResponse> modifyMember(@RequestBody PutPasswordDTO putPasswordDTO) throws CustomException {
        memberService.passwordModify(putPasswordDTO);
        return SuccessResponse.success(SuccessCode.SIGNUP_SUCCESS);
    }


}
