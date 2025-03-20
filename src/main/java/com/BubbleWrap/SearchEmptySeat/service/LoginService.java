package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.member.FindPasswordRequest;
import com.BubbleWrap.SearchEmptySeat.dto.member.LoginRequest;
import com.BubbleWrap.SearchEmptySeat.dto.member.SignUpRequest;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import com.BubbleWrap.SearchEmptySeat.security.JwtUtil;
import com.BubbleWrap.SearchEmptySeat.utils.FileStorageService;
import com.BubbleWrap.SearchEmptySeat.utils.RandomPassWord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MailService mailService;
    private final FileStorageService fileStorageService;

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> registerUser(SignUpRequest request, MultipartFile imageFile) {
        Optional<Member> existingUser = memberRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.EMAIL_ALREADY_EXISTS.getCode(), ErrorCode.EMAIL_ALREADY_EXISTS.getMessage()));
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Member newUser = new Member();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setName(request.getName());
        newUser.setPhone(request.getPhone());
        newUser.setLocation(request.getLocation());
        newUser.setUserType(request.getUserType());

        memberRepository.save(newUser);

        List<String> profileImagePath = new ArrayList<>();
        if (imageFile != null && !imageFile.isEmpty()) {
            profileImagePath.add(fileStorageService.saveSingleFile(newUser.getUserId(), "member/profile", "profile", imageFile));
        } else {
            profileImagePath.add("member/profile/default.png"); // 기본 프로필 이미지
        }
        newUser.setImage(profileImagePath);

        memberRepository.save(newUser);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("email", newUser.getEmail());
        responseData.put("name", newUser.getName());
        responseData.put("phone", newUser.getPhone());
        responseData.put("location", newUser.getLocation());
        responseData.put("image", newUser.getImage());
        responseData.put("userType", newUser.getUserType().name());

        return ResponseEntity.ok(ApiResponse.success(responseData, "Membership registration successful"));
    }




    public ResponseEntity<ApiResponse<Map<String, Object>>> loginUser(LoginRequest request) {
        Member user = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.INVALID_CREDENTIALS.getCode(), ErrorCode.INVALID_CREDENTIALS.getMessage()));
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getUserType().name());

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("email", user.getEmail());
        responseData.put("userId", String.valueOf(user.getUserId()));
        responseData.put("name", user.getName());
        responseData.put("phone", user.getPhone());
        responseData.put("location", user.getLocation());
        responseData.put("userType", user.getUserType().name());
        responseData.put("image", user.getImage());
        responseData.put("token", token);

        return ResponseEntity.ok(ApiResponse.success(responseData, "Login Success"));
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, String>>> resetPassword(FindPasswordRequest request){
        Member user = memberRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage()));

        String tempPassword = RandomPassWord.generateTemporaryPassword();
        user.setPassword(passwordEncoder.encode(tempPassword));
        memberRepository.save(user);

        String subject = "임시 비밀번호 안내";
        mailService.sendEmail(request.getEmail(), subject, tempPassword);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("email", request.getEmail());

        return ResponseEntity.ok(ApiResponse.success(responseData, "Reset password successful"));
    }
}
