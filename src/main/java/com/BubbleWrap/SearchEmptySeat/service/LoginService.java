package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
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
    public ResponseEntity<ApiResponse<String>> registerUser(SignUpRequest request) {
        Optional<Member> existingUser = memberRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body(ApiResponse.error("이미 존재하는 이메일입니다."));
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

        return ResponseEntity.ok(ApiResponse.success("회원가입이 완료되었습니다."));
    }

    public ResponseEntity<ApiResponse<Map<String, String>>> loginUser(LoginRequest request) {
        Member user = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다."));
        }

        // JWT 토큰 생성
        String token = jwtUtil.generateToken(user.getEmail(), user.getUserType().name());

        // 응답 데이터 구성
        Map<String, String> responseData = new HashMap<>();
        responseData.put("email", user.getEmail());
        responseData.put("name", user.getName());
        responseData.put("phone", user.getPhone());
        responseData.put("role", user.getUserType().name());
        responseData.put("token", token);

        return ResponseEntity.ok(ApiResponse.success(responseData));
    }
}
