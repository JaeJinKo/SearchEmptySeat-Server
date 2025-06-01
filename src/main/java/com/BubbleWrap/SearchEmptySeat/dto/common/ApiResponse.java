package com.BubbleWrap.SearchEmptySeat.dto.common;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse<T> {
    private String status;
    private T data;
    private String message;

    // Getter 메서드 추가
    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    // 성공 응답
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>("success", data, message);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", data, null);
    }

    // 오류 응답 (ErrorCode Enum 사용)
    public static <T> ApiResponse<T> error(String errorCode, String message) {
        Map<String, String> errorData = new HashMap<>();
        errorData.put("code", errorCode);
        return new ApiResponse<>("error", (T) errorData, message);
    }

    private ApiResponse(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}

