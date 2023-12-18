package com.swyg.picketbackend.auth.dto;

import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO {

    private String grantType;

    private String accessToken;

    private String refreshToken;

    private Long accessTokenExpiresIn;


}

