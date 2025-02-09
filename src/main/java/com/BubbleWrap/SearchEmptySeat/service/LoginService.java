package com.BubbleWrap.SearchEmptySeat.service;

import com.BubbleWrap.SearchEmptySeat.dto.login.LoginResponse;
import com.BubbleWrap.SearchEmptySeat.dto.login.SignUpRequest;
import com.BubbleWrap.SearchEmptySeat.dto.login.LoginRequest;
import com.BubbleWrap.SearchEmptySeat.model.Member;
import com.BubbleWrap.SearchEmptySeat.repository.MemberRepository;
import com.BubbleWrap.SearchEmptySeat.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ResponseEntity<LoginResponse>  registerUser(SignUpRequest request) {
        Optional<Member> existingUser = memberRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Member newUser = new Member();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodedPassword);
        newUser.setName(request.getName());
        newUser.setPhone(request.getPhone());
        newUser.setLocation(request.getLocation());

        if (request.getUserType() == null) {
            throw new IllegalArgumentException("UserType은 반드시 필요합니다.");
        }

        newUser.setUserType(request.getUserType());

        memberRepository.save(newUser);

        return ResponseEntity.ok(new LoginResponse(true, "회원가입이 완료되었습니다.", null));
    }

    public ResponseEntity<LoginResponse> loginUser(LoginRequest request) {
        Member user = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 이메일 또는 비밀번호입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 이메일 또는 비밀번호입니다.");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getUserType().name());

        return ResponseEntity.ok(new LoginResponse(true, "로그인 성공", token));
    }
}