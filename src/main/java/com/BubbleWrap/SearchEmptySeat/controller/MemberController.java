package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.member.MyInfoResponse;
import com.BubbleWrap.SearchEmptySeat.dto.member.MyInfoUpdateRequest;
import com.BubbleWrap.SearchEmptySeat.dto.member.SignUpRequest;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PatchMapping("/me/{userId}")
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

    @GetMapping("/me/{userId}")
    public ResponseEntity<ApiResponse<MyInfoResponse>> getMyInfo(@PathVariable Long userId) {
        return memberService.getMyInfo(userId);
    }
}
