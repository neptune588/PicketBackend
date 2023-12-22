package com.swyg.picketbackend.auth.dto.auth.res;

import lombok.*;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResponseDTO {

    private Long memberId;

    private String grantType;

    private String accessToken;

    private String refreshToken;

    private Long accessTokenExpiresIn;


}

