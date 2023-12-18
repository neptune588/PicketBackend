package com.swyg.picketbackend.global.exception;

import com.swyg.picketbackend.global.util.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends Exception {

    private final ErrorCode errorCode;

}
