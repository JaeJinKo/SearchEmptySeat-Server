package com.BubbleWrap.SearchEmptySeat.dto.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // ERROR-0000 공용
    INTERNAL_SERVER_ERROR("ERROR-0001", "서버 내부 오류가 발생했습니다."),
    INVALID_INPUT("ERROR-0002", "입력값이 유효하지 않습니다."),
    INVALID_ARGUMENT("ERROR-0003", "잘못된 입력값입니다."),
    JSON_PROCESSING_ERROR("ERROR-0004", "JSON 변환 중 오류가 발생했습니다."),
    // ERROR-1000 회원 관련
    USER_NOT_FOUND("ERROR-1001", "사용자를 찾을 수 없습니다."),
    INVALID_CREDENTIALS("ERROR-1002", "아이디 또는 비밀번호가 올바르지 않습니다."),
    EMAIL_ALREADY_EXISTS("ERROR-1003", "이미 존재하는 이메일입니다."),
    // ERROR-2000 가게 관련
    STORE_NOT_FOUND("ERROR-2001","가게 ID를 찾을 수 없습니다."),
    INVALID_CATEGORY("ERROR-2002", "유효하지 않은 카테고리입니다.")
    // ERROR-3000

    ;


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
