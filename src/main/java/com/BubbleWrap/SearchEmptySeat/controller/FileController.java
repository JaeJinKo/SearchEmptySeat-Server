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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    @Value("${myapp.file.upload-dir}")
    private String uploadDir;

    /**
     * 이미지 파일 서빙
     * ex) GET /api/files/member/profile/2_profile.png
     */
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
}
