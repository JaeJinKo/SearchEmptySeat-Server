package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.member.MyInfoResponse;
import com.BubbleWrap.SearchEmptySeat.dto.member.MyInfoUpdateRequest;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 인코딩(있다면)

    /**
     * application.yml에서 설정한 업로드 기본 경로
     * 예) myapp.file.upload-dir: ./uploads
     */
    @Value("${myapp.file.upload-dir}")
    private String baseUploadDir;

    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<MyInfoResponse>> getMyInfo(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.USER_NOT_FOUND));

        MyInfoResponse response = new MyInfoResponse(member);

        return ResponseEntity.ok(
                ApiResponse.success(response, "Get my info success")
        );
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateMyInfo(Long userId, MyInfoUpdateRequest request) {
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

        // 이미지 처리 (단일 이미지)
        MultipartFile imageFile = request.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            File baseDirFile = new File(baseUploadDir).getAbsoluteFile();

            File profileDir = new File(baseDirFile, "member/profile");
            if (!profileDir.exists()) {
                profileDir.mkdirs();
            }

            String originalFilename = imageFile.getOriginalFilename();
            String extension = ".png";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            }

            String newFilename = userId + "_profile" + extension;
            File dest = new File(profileDir, newFilename);

            try {
                imageFile.transferTo(dest);
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.IMAGE_SAVE_ERROR);
            }

            // 6) DB에 저장할 경로(웹 표시용). 예를 들어 "member/profile/1_profile.png"
            String dbPath = "member/profile/" + newFilename;

            List<String> currentImages = member.getImage();
            if (currentImages == null) {
                currentImages = new ArrayList<>();
            }
            currentImages.clear();
            currentImages.add(dbPath);

            member.setImage(currentImages);

            responseData.put("image", currentImages);
        }

        member.setUpdatedDate(LocalDateTime.now());

        return ResponseEntity.ok(ApiResponse.success(responseData, "Update my info success"));
    }
}
