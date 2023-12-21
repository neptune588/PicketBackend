package com.swyg.picketbackend.global.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {

    //200 OK
    SIGNUP_SUCCESS(HttpStatus.OK,"회원가입에 성공하였습니다."),

    SELECT_SUCCESS(HttpStatus.OK,"SELECT_SUCCESS"),

    UPDATE_SUCCESS(HttpStatus.OK,"UPDATE_SUCCESS"),

    BOARD_UPDATE_SUCCESS(HttpStatus.OK,"버킷을 수정하였습니다."),
    BOARD_DELETE_SUCCESS(HttpStatus.OK,"버킷을 삭제하였습니다."),

    DELETE_SUCCESS(HttpStatus.OK,"DELETE_SUCCESS"),

    INSERT_SUCCESS(HttpStatus.OK,"INSERT_SUCCESS");

    private final HttpStatus status;
    private final String message;

}
