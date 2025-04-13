package com.BubbleWrap.SearchEmptySeat.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.member.MyInfoResponse;
import com.BubbleWrap.SearchEmptySeat.dto.member.MyInfoUpdateRequest;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import com.BubbleWrap.SearchEmptySeat.utils.FileStorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<MyInfoResponse>> getMyInfo(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        MyInfoResponse response = new MyInfoResponse(member);
        return ResponseEntity.ok(ApiResponse.success(response, "Get my info success"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateMyInfo(Long userId, MyInfoUpdateRequest request, MultipartFile imageFile) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            member.setEmail(request.getEmail());
        }
        if (request.getName() != null && !request.getName().isBlank()) {
            member.setName(request.getName());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            String encoded = passwordEncoder.encode(request.getPassword());
            member.setPassword(encoded);
        }
        if (request.getLocation() != null && !request.getLocation().isBlank()) {
            member.setLocation(request.getLocation());
        }
    
        if (imageFile != null && !imageFile.isEmpty()) {
            String savedImagePath = fileStorageService.saveSingleFile(userId, "member/profile", "profile", imageFile);
    
            List<String> currentImages = member.getImage();
            if (currentImages == null) {
                currentImages = new ArrayList<>();
            }
            currentImages.clear();
            currentImages.add(savedImagePath);
    
            member.setImage(currentImages);
        }
    
        member.setUpdatedDate(LocalDateTime.now());
    
        // 최신 정보를 다시 읽어옴
        Member updatedMember = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("email", updatedMember.getEmail());
        responseData.put("userId", String.valueOf(updatedMember.getUserId()));
        responseData.put("name", updatedMember.getName());
        responseData.put("phone", updatedMember.getPhone());
        responseData.put("location", updatedMember.getLocation());
        responseData.put("userType", updatedMember.getUserType().name());
        responseData.put("image", updatedMember.getImage());
    
        return ResponseEntity.ok(ApiResponse.success(responseData, "Update my info success"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<String>> updatePassword(Long userId, String newPassword) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (newPassword == null || newPassword.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT);
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        member.setPassword(encodedPassword);
        member.setUpdatedDate(LocalDateTime.now());

        return ResponseEntity.ok(ApiResponse.success("Password updated successfully", "Password update success"));
    }
}
