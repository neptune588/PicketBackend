package com.swyg.picketbackend.auth.oauth;



import com.swyg.picketbackend.auth.dto.auth.req.LoginDTO;
import com.swyg.picketbackend.auth.service.AuthService;
import com.swyg.picketbackend.auth.util.PrincipalDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler { // oauth 소셜 로그인 성공후 성공 Handler

    private final AuthService authService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        String email  = principalDetails.getName();

        String password = "google";

//        SecurityContext securityContext = SecurityContextHolder.getContext();
//
//        securityContext.setAuthentication(null);

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setEmail(email);
        loginDTO.setPassword(password);

        ResponseEntity<String> tokenResponse = sendLoginRequest(loginDTO);

        log.info(String.valueOf(tokenResponse));

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private ResponseEntity<String> sendLoginRequest(LoginDTO loginDTO) { // 로그인 요청
        String loginUrl = "http://localhost:8080/auth/login";

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        // Set up the request entity
        HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);

        // Send the request
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(loginUrl, requestEntity, String.class);
    }

}
