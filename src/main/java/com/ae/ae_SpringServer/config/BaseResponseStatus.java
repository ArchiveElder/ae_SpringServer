package com.ae.ae_SpringServer.config;

import lombok.Getter;

/**
 *에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 :요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request오류
     */
// Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /record
    POST_RECORD_NO_IMAGE(false, 2100, "이미지가 없습니다."),
    POST_RECORD_NO_TEXT(false, 2101, "식단 메뉴를 입력해주세요."),
    POST_RECORD_NO_CALORY(false, 2102, "칼로리를 입력해주세요."),
    POST_RECORD_MINUS_CALORY(false, 2103, "칼로리가 0보다 작습니다."),
    POST_RECORD_NO_CARB(false, 2104, "탄수화물을 입력해주세요."),
    POST_RECORD_MINUS_CARB(false, 2105, "탄수화물이 0보다 작습니다."),
    POST_RECORD_NO_PROTEIN(false, 2106, "단백질을 입력해주세요."),
    POST_RECORD_MINUS_PROTEIN(false, 2107, "단백질이 0보다 작습니다."),
    POST_RECORD_NO_FAT(false, 2108, "지방을 입력해주세요."),
    POST_RECORD_MINUS_FAT(false, 2109, "지방이 0보다 작습니다."),
    POST_RECORD_NO_RDATE(false, 2110, "식사 날짜를 입력해주세요."),
    POST_RECORD_INVALID_RDATE(false, 2111, "식사 날짜의 형식이 맞지 않습니다."),
    POST_RECORD_NO_RTIME(false, 2112, "식사 시간을 입력해주세요."),
    POST_RECORD_INVALID_RTIME(false, 2113, "식사 시간의 형식이 맞지 않습니다."),
    POST_RECORD_NO_AMOUNT(false, 2114, "식사 양을 입력해주세요."),
    POST_RECORD_MINUS_AMOUNT(false, 2115, "식사 양이 0보다 작습니다."),
    POST_RECORD_NO_MEAL(false, 2116, "식사 끼니를 입력해주세요."),
    POST_RECORD_INVALID_MEAL(false, 2117, "식사 끼니가 유효하지 않습니다."),
    POST_RECORD_NO_ID(false, 2118, "조회하고 싶은 기록 id를 입력해주세요."),
    POST_RECORD_NO_RECORD_DATA(false, 2119, "해당하는 기록이 없습니다."),
    POST_DETAIL_NO_RECORD_ID(false, 2150, "기록id에 해당하는 상세정보가 없습니다."),

    // [POST] /user-nickname
    POST_EMPTY_NICKNAME(false, 2161, "닉네임을 입력해주세요. "),


    // [POST] /food
    POST_FOOD_NO_ID(false, 2120, "조회하고 싶은 음식 id를 입력해주세요."),

    // [PUT] /userupdate
    PUT_USER_NO_AGE(false, 2121, "나이를 입력해주세요."),
    PUT_USER_MINUS_AGE(false, 2122, "나이가 1보다 작습니다."),
    PUT_USER_NO_HEIGHT(false, 2123, "키를 입력해주세요."),
    PUT_USER_MINUS_HEIGHT(false, 2124, "키가 0보다 작습니다."),
    PUT_USER_NO_WEIGHT(false, 2125, "몸무게를 입력해주세요."),
    PUT_USER_MINUS_WEIGHT(false, 2126, "몸무게가 0보다 작습니다."),
    PUT_USER_NO_ACTIVITY(false, 2127, "활동 점수를 입력해주세요."),
    PUT_USER_INVALID_ACTIVITY(false, 2128, "활동 점수가 유효하지 않습니다."),

    // [POST] /signup
    POST_USER_NO_NAME(false, 2129, "이름을 입력해주세요."),
    POST_USER_LONG_NAME(false, 2130, "이름이 45자보다 깁니다."),
    POST_USER_MINUS_AGE(false, 2132, "나이가 1보다 작습니다."),
    POST_USER_NO_GENDER(false, 2133, "성별을 입력해주세요."),
    POST_USER_INVALID_GENDER(false, 2134, "성별이 유효하지 않습니다."),
    POST_USER_NO_HEIGHT(false, 2135, "키를 입력해주세요."),
    POST_USER_MINUS_HEIGHT(false, 2136, "키가 0보다 작습니다."),
    POST_USER_NO_WEIGHT(false, 2137, "몸무게를 입력해주세요."),
    POST_USER_MINUS_WEIGHT(false, 2138, "몸무게가 0보다 작습니다."),
    POST_USER_NO_ACTIVITY(false, 2139, "활동 점수를 입력해주세요."),
    POST_USER_INVALID_ACTIVITY(false, 2140, "활동 점수가 유효하지 않습니다."),
    POST_USER_NO_NICKNAME(false, 2141, "닉네임을 입력해주세요."),
    POST_USER_LONG_NICKNAME(false, 2142, "닉네임이 45자보다 깁니다."),

    // [POST] /login
    POST_USER_NO_TOKEN(false, 2141, "accessToken을 입력해주세요."),

    // [POST] /bistro
    POST_BISTRO_NO_WIDE(false, 2142, "wide를 입력해주세요."),
    POST_BISTRO_NO_MIDDLE(false, 2143, "middle을 입력해주세요."),

    // [POST] / bookmark
    POST_BOOKMARK_NO_BISTRO_ID(false, 2144, "bistro_id를 입력해주세요. "),
    POST_BOOKMARK_PRESENT_BISTRO(false, 2145, "이미 북마크한 bistro입니다. "),
    POST_BOOKMARK_LIST_EMPTY(false, 2146, "북마크한 식당이 없습니다"),
    POST_BOOMARK_THERE_NO_BISTRO(false, 2147, "북마크 한적 없는 식당을 북마크 해제 하고있습니다"),
    POST_BOOKMARK_WRONG_BISTRO(false, 2150, "북마크 할 수 없는 식당의 id입니다. "),

    // [POST] /record-update
    POST_EMPTY_NO_RECORD_ID(false, 2148, "recordId를 입력해주세요. "),
    POST_INVALID_RECORD(false, 2149, "기록한 식단이 없습니다. "),


    /**
     * 3000 : Response오류
     */
// Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),



    /**
     * 4000 : Database, Server오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다.");




    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
