package com.swyg.picketbackend.global.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /*400 BAD REQUEST : 클라이언트의 요청이 유효하지 않음*/
    PARAMETER_NOT_VALID(HttpStatus.BAD_REQUEST,"유효하지 않은 파라미터입니다."),

    // JWT 관련
    NOT_VALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
    UNAUTHENTICATION_ACCESS(HttpStatus.UNAUTHORIZED,"인증 회원 정보가 없습니다."),


    /*401 BAD Unauthorized : 권한 없음*/
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED,"접근 권한이 없습니다."),

    /*403 Forbidden : 접근 금지*/
    FORBIDDEN(HttpStatus.FORBIDDEN,"접근 권한이 없습니다."),



    /*409 CONFLICT : 리소스가 현재 상태와 충돌,보통 중복된 데이터 존재*/
    DUPLICATE_EMAIL(HttpStatus.CONFLICT,"이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT,"이미 존재하는 닉네임입니다."),

    /*404 NOT_FOUND : 요청 리소스를 찾을 수 없음*/
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 업습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청 리소스를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
