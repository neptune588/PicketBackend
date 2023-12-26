package com.swyg.picketbackend.auth.controller;

import com.swyg.picketbackend.auth.dto.member.PatchMemberRequestDTO;
import com.swyg.picketbackend.auth.dto.member.PostNicknameRequestDTO;
import com.swyg.picketbackend.auth.service.MemberService;
import com.swyg.picketbackend.global.exception.CustomException;
import com.swyg.picketbackend.global.util.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Log4j2
@Tag(name = "MemberController", description = "회원 관련 api")
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @Operation(summary = "로그인 회원 정보 수정 닉네임 중복 체크", description = "로그인 회원 정보 수정 닉네임 중복 체크")
    @PostMapping("/profile/check-nickname")
    public ResponseEntity<?> checkNickname(@RequestBody PostNicknameRequestDTO postNicknameRequestDTO) {
        memberService.nicknameCheck(postNicknameRequestDTO.getNickname());
        return ResponseEntity.ok("사용할 수 있는 닉네임입니다.");
    }

    @Operation(summary = "로그인 회원 프로필 수정", description = "로그인 회원 프로필 수정")
    @PatchMapping("/profile")
    public ResponseEntity<?> memberModify(
            @RequestPart PatchMemberRequestDTO patchMemberRequestDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                log.info("프로필 이미지 파일이 있을 경우 modify...");
                memberService.modifyMemberWithFile(patchMemberRequestDTO, file);
            } else {
                log.info("첨부파일 null 일 경우 modify...");
                memberService.modifyMember(patchMemberRequestDTO);
            }
        } catch (IOException e) {
            throw new CustomException(ErrorCode.IO_ERROR);
        }
        return ResponseEntity.ok("회원 프로필이 수정되었습니다.");
    }
}
