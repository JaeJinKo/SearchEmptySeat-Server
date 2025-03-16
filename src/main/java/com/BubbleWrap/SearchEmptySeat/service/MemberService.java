package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.member.MyInfoResponse;
import com.BubbleWrap.SearchEmptySeat.dto.member.MyInfoUpdateRequest;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import com.BubbleWrap.SearchEmptySeat.utils.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

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

        Map<String, Object> responseData = new HashMap<>();

        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            member.setEmail(request.getEmail());
            responseData.put("email", member.getEmail());
        }
        if (request.getName() != null && !request.getName().isBlank()) {
            member.setName(request.getName());
            responseData.put("name", member.getName());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            String encoded = passwordEncoder.encode(request.getPassword());
            member.setPassword(encoded);
            responseData.put("password", "password updated");
        }
        if (request.getLocation() != null && !request.getLocation().isBlank()) {
            member.setLocation(request.getLocation());
            responseData.put("location", member.getLocation());
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
            responseData.put("image", currentImages);
        }

        member.setUpdatedDate(LocalDateTime.now());

        return ResponseEntity.ok(ApiResponse.success(responseData, "Update my info success"));
    }
}
