package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.dto.login.LoginRequest;
import com.BubbleWrap.SearchEmptySeat.dto.login.SignUpRequest;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import com.BubbleWrap.SearchEmptySeat.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public LoginService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public ResponseEntity<ApiResponse<Map<String, Object>>> registerUser(SignUpRequest request) {
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

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("email", newUser.getEmail());
        responseData.put("name", newUser.getName());
        responseData.put("phone", newUser.getPhone());
        responseData.put("location", newUser.getLocation());
        responseData.put("role", newUser.getUserType().name());

        return ResponseEntity.ok(ApiResponse.success(responseData, "Membership registration successful"));
    }




    public ResponseEntity<ApiResponse<Map<String, String>>> loginUser(LoginRequest request) {
        Member user = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getMessage()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ErrorCode.INVALID_CREDENTIALS.getCode(), ErrorCode.INVALID_CREDENTIALS.getMessage()));
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getUserType().name());

        Map<String, String> responseData = new HashMap<>();
        responseData.put("email", user.getEmail());
        responseData.put("name", user.getName());
        responseData.put("phone", user.getPhone());
        responseData.put("role", user.getUserType().name());
        responseData.put("token", token);

        return ResponseEntity.ok(ApiResponse.success(responseData, "Login Success"));
    }
}
