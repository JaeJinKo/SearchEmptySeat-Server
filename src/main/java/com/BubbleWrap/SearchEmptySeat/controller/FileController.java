package com.BubbleWrap.SearchEmptySeat.controller;

import com.BubbleWrap.SearchEmptySeat.dto.common.ApiResponse;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    @Value("${myapp.file.upload-dir}")
    private String uploadDir;

    // 허용된 파일 확장자
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
        ".png", ".jpg", ".jpeg", ".gif", ".webp"
    );

    // 허용된 MIME 타입
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
        "image/png", "image/jpeg", "image/jpg", "image/gif", "image/webp"
    );

    // 최대 파일 크기 (100MB)
    private static final long MAX_FILE_SIZE = 100 * 1024 * 1024;

    /**
     * 유연한 경로 매칭으로 이미지 파일 서빙
     * ex) GET /api/files/member/profile/uuid_profile.png
     * ex) GET /api/files/store/1/menu/uuid_menu.png
     */
    @GetMapping("/**")
    public ResponseEntity<?> serveFile(HttpServletRequest request) {
        String path = request.getRequestURI().replace("/api/files/", "");
        
        // 1. 경로 순회 공격 방지
        if (path.contains("..") || path.contains("//") || path.contains("\\")) {
            throw new BusinessException(ErrorCode.INVALID_ARGUMENT);
        }
        
        // 2. 허용된 경로 패턴 검증
        if (!isValidPathPattern(path)) {
            throw new BusinessException(ErrorCode.INVALID_ARGUMENT);
        }
        
        // 3. 파일 확장자 검증
        if (!isValidFileExtension(path)) {
            throw new BusinessException(ErrorCode.INVALID_ARGUMENT);
        }
        
        String fullPath = uploadDir + File.separator + path;
        File file = new File(fullPath);
        
        // 4. 파일 존재 및 타입 검증
        if (!file.isFile() || !file.exists()) {
            return ResponseEntity.notFound().build();
        }
        
        // 5. 파일 크기 검증
        if (file.length() > MAX_FILE_SIZE) {
            throw new BusinessException(ErrorCode.SIZE_TO_LARGE);
        }

        try {
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            String mimeType = Files.probeContentType(file.toPath());
            
            // 6. MIME 타입 검증
            if (!StringUtils.hasText(mimeType) || !ALLOWED_MIME_TYPES.contains(mimeType)) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            ByteArrayResource resource = new ByteArrayResource(fileBytes);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(resource);

        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_NOT_POUND);
        }
    }

    /**
     * 허용된 경로 패턴 검증
     */
    private boolean isValidPathPattern(String path) {
        // 허용된 패턴들
        String[] allowedPatterns = {
            "store/\\d+/menu/.*\\.(png|jpg|jpeg|gif|webp)",
            "member/profile/.*\\.(png|jpg|jpeg|gif|webp)",
            "store/\\d+/.*\\.(png|jpg|jpeg|gif|webp)",
            "review/\\d+/.*\\.(png|jpg|jpeg|gif|webp)"
        };
        
        return Arrays.stream(allowedPatterns)
            .anyMatch(pattern -> path.matches(pattern));
    }

    /**
     * 파일 확장자 검증
     */
    private boolean isValidFileExtension(String path) {
        String lowerPath = path.toLowerCase();
        return ALLOWED_EXTENSIONS.stream()
            .anyMatch(ext -> lowerPath.endsWith(ext));
    }

    /*
    // 기존 엔드포인트 (주석처리)
    @GetMapping("/{folder1}/{folder2}/{filename}")
    public ResponseEntity<?> serveFile(
            @PathVariable String folder1,
            @PathVariable String folder2,
            @PathVariable String filename
    ) {
        String path = uploadDir
                + File.separator + folder1
                + File.separator + folder2
                + File.separator + filename;

        File file = new File(path);
        if (!file.exists()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        try {
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            String mimeType = Files.probeContentType(file.toPath());
            if (!StringUtils.hasText(mimeType)) {
                mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }

            ByteArrayResource resource = new ByteArrayResource(fileBytes);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(resource);

        } catch (IOException e) {
            throw new BusinessException(ErrorCode.FILE_NOT_POUND);
        }
    }
    */
}
