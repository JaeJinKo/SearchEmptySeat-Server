package com.BubbleWrap.SearchEmptySeat.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.member.MyInfoResponse;
import com.BubbleWrap.SearchEmptySeat.dto.member.MyInfoUpdateRequest;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/{userId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateMyInfo(
            @PathVariable Long userId,
            @RequestPart("data") String userData,
            @RequestPart(value = "image", required = false) MultipartFile imageFile
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        MyInfoUpdateRequest request;
        try {
            request = objectMapper.readValue(userData, MyInfoUpdateRequest.class);
        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.JSON_PROCESSING_ERROR);
        }
        return memberService.updateMyInfo(userId, request, imageFile);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<MyInfoResponse>> getMyInfo(@PathVariable Long userId) {
        return memberService.getMyInfo(userId);
    }
    
    @PatchMapping("/{userId}/password")
    public ResponseEntity<ApiResponse<String>> updatePassword(
            @PathVariable Long userId,
            @RequestBody Map<String, String> requestBody
    ) {
        String newPassword = requestBody.get("newPassword");
        return memberService.updatePassword(userId, newPassword);
    }
}
