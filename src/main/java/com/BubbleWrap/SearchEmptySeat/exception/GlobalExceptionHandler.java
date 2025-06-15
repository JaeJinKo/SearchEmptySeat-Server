package com.BubbleWrap.SearchEmptySeat.exception;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // IllegalArgumentException 처리 (잘못된 입력값)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.INVALID_ARGUMENT.getCode(), ErrorCode.INVALID_ARGUMENT.getMessage()));
    }

    // 유효하지 않은 요청 데이터 (DTO @Valid 실패)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.INVALID_INPUT.getCode(), ErrorCode.INVALID_INPUT.getMessage()));
    }

    // 데이터 무결성 위반 (중복 키 등)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message = ex.getMessage();
        if (message != null && message.contains("business_registration_number")) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.BUSINESS_REGISTRATION_NUMBER_ALREADY_EXISTS.getCode(), ErrorCode.BUSINESS_REGISTRATION_NUMBER_ALREADY_EXISTS.getMessage()));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.INVALID_INPUT.getCode(), "데이터 무결성 위반: " + ex.getMessage()));
    }

    // 예상하지 못한 서버 오류 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralException(Exception ex) {
        return ResponseEntity.internalServerError().body(ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponse<String>> handleMaxUploadSize(MaxUploadSizeExceededException ex) {
        return ResponseEntity.badRequest().body(
                ApiResponse.error(ErrorCode.EMAIL_SEND_ERROR.getCode(), ErrorCode.SIZE_TO_LARGE.getMessage())
        );
    }
}
