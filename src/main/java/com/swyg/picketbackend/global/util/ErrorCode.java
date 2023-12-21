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
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
    UNAUTHENTICATION_ACCESS(HttpStatus.UNAUTHORIZED,"인증 회원 정보가 없습니다."),
    INVAILD_SIGNAUTRE(HttpStatus.BAD_REQUEST,"잘못된 JWT 서명입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST,"지원되지 않는 JWT 토큰입니다."),
    Illegal_TOKEN(HttpStatus.BAD_REQUEST,"잘못된 JWT 토큰입니다."),


    /*401 BAD Unauthorized : 권한 없음*/
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED,"접근 권한이 없습니다."),
    UNAUTHORIZED_UPDATE(HttpStatus.UNAUTHORIZED,"버킷 수정 권한이 없습니다."),
    UNAUTHORIZED_DELETE(HttpStatus.UNAUTHORIZED,"버킷 삭제 권한이 없습니다."),


    /*403 Forbidden : 접근 금지*/
    FORBIDDEN(HttpStatus.FORBIDDEN,"접근 권한이 없습니다."),


    /*409 CONFLICT : 리소스가 현재 상태와 충돌,보통 중복된 데이터 존재*/
    DUPLICATE_EMAIL(HttpStatus.CONFLICT,"이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT,"이미 존재하는 닉네임입니다."),
    INSERT_FAILED(HttpStatus.CONFLICT,"INSERT에 실패하였습니다."),

    /*404 NOT_FOUND : 요청 리소스를 찾을 수 없음*/
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일의 회원을 찾을 수 업습니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청 리소스를 찾을 수 없습니다."),
    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 버킷을 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String message;
}
