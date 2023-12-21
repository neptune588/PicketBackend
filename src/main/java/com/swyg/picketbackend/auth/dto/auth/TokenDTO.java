package com.swyg.picketbackend.auth.dto.auth;

import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO {

    private Long memberId;

    private String grantType;

    private String accessToken;

    private String refreshToken;

    private Long accessTokenExpiresIn;


}

