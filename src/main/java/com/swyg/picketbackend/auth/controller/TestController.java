package com.swyg.picketbackend.auth.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String accessTest() {
        return "인증 성공";
    }
}
