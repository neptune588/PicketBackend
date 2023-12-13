package com.swyg.picketbackend.user.controller;

import com.swyg.picketbackend.global.dto.SuccessResponse;
import com.swyg.picketbackend.global.util.SuccessCode;
import com.swyg.picketbackend.user.dto.SignupDTO;
import com.swyg.picketbackend.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/signUp")
    public ResponseEntity<SuccessResponse> signup(@RequestBody @Valid SignupDTO signupDTO) {

        userService.register(signupDTO);

        return SuccessResponse.success(SuccessCode.INSERT_SUCCESS);
    }

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwt 요청 성공";
    }
}
