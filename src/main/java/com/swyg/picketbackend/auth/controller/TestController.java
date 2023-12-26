package com.swyg.picketbackend.auth.controller;


import com.swyg.picketbackend.auth.dto.auth.res.SignupResponseDTO;
import com.swyg.picketbackend.auth.service.AuthService;
import com.swyg.picketbackend.global.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @Operation(summary = "Amazons s3 이미지 삭제 테스트", description = "Amazons s3 이미지 테스트")
    @PostMapping("/auth/amazonS3Delete")
    public ResponseEntity<?> s3TestDelete(@RequestBody String fileName) {
        Boolean isDeleted = authService.deleteTestS3(fileName);
        return ResponseEntity.ok(isDeleted ? 1 : 0);
    }

}
