package com.swyg.picketbackend.auth.controller;



import com.swyg.picketbackend.auth.dto.auth.MemberResponseDTO;
import com.swyg.picketbackend.auth.service.AuthService;
import com.swyg.picketbackend.global.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    public String accessTest() throws CustomException {

        return "토큰 인증 성공";
    }

    @Operation(summary = "권한 테스트", description = "본인 권한 url 접근 권한")
    @GetMapping("/auth/{memberId}")
    public MemberResponseDTO accessMemberTest(@PathVariable("memberId") Long memberId) throws CustomException {

        return authService.findMember(memberId);
    }

}
