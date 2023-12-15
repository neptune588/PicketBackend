package com.swyg.picketbackend.auth.controller;


import com.swyg.picketbackend.auth.dto.MemberResponseDTO;
import com.swyg.picketbackend.auth.service.AuthService;
import com.swyg.picketbackend.auth.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "TestController", description = "인증 관련 API test")
@Log4j2
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final AuthService authService;

    @Operation(summary = "인증 테스트", description = "인증 성공하면 return")
    @GetMapping("/auth")
    public String accessTest() {
        log.info("인증 회원 ID :" + SecurityUtil.getCurrentMemberId());
        return "토큰 인증 성공";
    }

    @GetMapping("/auth/{member_id}")
    public ResponseEntity<MemberResponseDTO> memberList(@PathVariable("member_id") Long id) {
        return ResponseEntity.ok(authService.findMember(id));
    }


}
