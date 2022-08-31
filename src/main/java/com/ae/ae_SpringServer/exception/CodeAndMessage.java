package com.ae.ae_SpringServer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeAndMessage {


    FAILED_TO_FIND_AVAILABLE_RSA("1004", "사용 가능한 키가 없습니다."),
    INVALID_APPLE_ACCESS("1005", "애플 로그인 서버에 접근 중 예외가 발생했습니다."),
    INVALID_APPLE_TOKEN("1006", "애플 Identity Token이 유효하지 않습니다.");
    private final String code;
    private final String message;
}
