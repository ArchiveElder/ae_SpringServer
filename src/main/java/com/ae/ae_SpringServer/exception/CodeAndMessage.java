package com.ae.ae_SpringServer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeAndMessage {
    // 유저 관련 1000 ~
    EMPTY_USER("1000", "해당 유저가 없습니다"),
    EMPTY_PATHVARIABLE_USERID("1001", "userIdx pathvariable이 없습니다"),

    FAILED_TO_FIND_AVAILABLE_RSA("1004", "사용 가능한 키가 없습니다."),
    INVALID_APPLE_ACCESS("1005", "애플 로그인 서버에 접근 중 예외가 발생했습니다."),
    INVALID_APPLE_TOKEN("1006", "애플 Identity Token이 유효하지 않습니다."),

    // 음식점 관련 1500 ~
    MAIN_CATEGORY_NO_CONTENT("1500", "대분류 카테고리를 입력해주세요."),
    MAIN_CATEGORY_LONG_CONTENT("1501", "대분류 카테고리가 너무 깁니다."),
    MIDDLE_CATEGORY_NO_CONTENT("1502", "중분류 카테고리를 입력해주세요."),
    MIDDLE_CATEGORY_LONG_CONTENT("1503", "중분류 카테고리가 너무 깁니다."),

    SITE_WIDE_NO_CONTENT("1504", "위치 대분류를 입력해주세요."),
    SITE_WIDE_LONG_CONTENT("1505", "위치 대분류가 너무 깁니다."),

    SITE_MIDDLE_NO_CONTENT("1506", "위치 중분류를 입력해주세요."),
    SITE_MIDDLE_LONG_CONTENT("1507", "위치 중분류가 너무 깁니다."),

    // S3 Util
    FILE_CONVERT_ERROR("1200", "파일 변환에 실패했습니다."),
    FILE_EXTENSION_ERROR("1201", "파일 확장자 인식에 실패했습니다.");

    private final String code;
    private final String message;
}
