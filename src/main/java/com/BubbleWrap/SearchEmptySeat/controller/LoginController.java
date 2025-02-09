package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.login.LoginRequest;
import com.BubbleWrap.SearchEmptySeat.dto.login.SignUpRequest;
import com.BubbleWrap.SearchEmptySeat.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final LoginService authService;

    public LoginController(LoginService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@Valid @RequestBody SignUpRequest request) {
        authService.registerUser(request);
        return ResponseEntity.ok("signup complete");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.loginUser(request);
        return ResponseEntity.ok(token);
    }
}