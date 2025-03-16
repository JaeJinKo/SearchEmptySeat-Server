package com.BubbleWrap.SearchEmptySeat.dto.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // ERROR-0000 공용
    INTERNAL_SERVER_ERROR("ERROR-0001", "서버 내부 오류가 발생했습니다."),
    INVALID_INPUT("ERROR-0002", "입력값이 유효하지 않습니다."),
    INVALID_ARGUMENT("ERROR-0003", "잘못된 입력값입니다."),
    JSON_PROCESSING_ERROR("ERROR-0004", "JSON 변환 중 오류가 발생했습니다."),
    EMAIL_SEND_ERROR("ERROR-0005", "EMAIL 전송을 실패했습니다."),
    IMAGE_SAVE_ERROR("ERROR-0006", "이미지 저장을 실패했습니다."),
    SIZE_TO_LARGE("ERROR-0007", "파일 용량이 너무 큽니다."),
    FILE_NOT_POUND("ERROR-0008", "파일을 찾을 수 없습니다."),
    // ERROR-0100 회원 관련
    USER_NOT_FOUND("ERROR-0101", "사용자를 찾을 수 없습니다."),
    INVALID_CREDENTIALS("ERROR-0102", "아이디 또는 비밀번호가 올바르지 않습니다."),
    EMAIL_ALREADY_EXISTS("ERROR-0103", "이미 존재하는 이메일입니다."),
    JWT_NOT_SET_PROPERLY("ERROR-0104","JWT secret key is not set properly"),
    EXPIRED_JWT("ERROR-0105","Expired JWT"),
    INVALID_JWT("ERROR-0106", "Invalid JWT"),
    JWT_VERIFICATION_FAILED("ERROR-0107","JWT verification failed"),
    ONLY_USER("ERROR-0108", "This feature is available only to regular members."),
    // ERROR-0200 가게 관련
    STORE_NOT_FOUND("ERROR-0201","가게 ID를 찾을 수 없습니다."),
    INVALID_CATEGORY("ERROR-0202", "유효하지 않은 카테고리입니다."),
    MENU_NOT_FOUND("ERROR-0203", "Menu not found"),
    UNAUTHORIZED_ACCESS("ERROR-0204", "Not the owner of the store."),
    ALREADY_FAVORITED("ERROR-0205", "This is a store that has already been added to list."),
    FAVORITE_NOT_FOUND("ERROR-0206", "The store has already been removed from list."),
    STORE_VIEWS_NOT_FOUND("ERROR-0207", "The store view information could not be verified."),
    // ERROR-0300

    NOT_PUOND("ERROR-9999", "test")
    ;


    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
