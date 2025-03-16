package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.member.FindPasswordRequest;
import com.BubbleWrap.SearchEmptySeat.dto.member.LoginRequest;
import com.BubbleWrap.SearchEmptySeat.dto.member.SignUpRequest;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.service.LoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Map<String, Object>>> register(
            @RequestPart("data") String userData,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        SignUpRequest request;
        try {
            request = objectMapper.readValue(userData, SignUpRequest.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JSON_PROCESSING_ERROR);
        }
        return loginService.registerUser(request, imageFile);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@Valid @RequestBody LoginRequest request) {
        return loginService.loginUser(request);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<Map<String, String>>> forgotPassword(@RequestBody FindPasswordRequest request){
        return loginService.resetPassword(request);
    }
}
