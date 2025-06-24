package com.BubbleWrap.SearchEmptySeat.utils;

import com.BubbleWrap.SearchEmptySeat.dto.common.ErrorCode;
import com.BubbleWrap.SearchEmptySeat.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStorageService {

    // application.yml에 설정한 업로드 기본 경로 (예: ./uploads)
    @Value("${myapp.file.upload-dir}")
    private String baseUploadDir;

    /**
     * 단일 파일을 저장하는 메서드
     * @param userId 파일명 생성 시 사용할 사용자 ID
     * @param subDirectory 저장할 하위 폴더 (예: "member/profile")
     * @param fileName 파일명에 붙일 접두어 (예: "profile")
     * @param file 저장할 MultipartFile
     * @return 저장된 파일의 상대 경로 (예: "member/profile/uuid_profile.png")
     */
    public String saveSingleFile(Long userId, String subDirectory, String fileName, MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_ARGUMENT);
        }

        // baseUploadDir를 절대 경로로 변환하고, 저장할 하위 폴더 생성
        File baseDir = new File(baseUploadDir).getAbsoluteFile();
        File targetDir = new File(baseDir, subDirectory);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        // 원본 파일명에서 확장자 추출, 없으면 기본 .png 사용
        String originalFilename = file.getOriginalFilename();
        String extension = ".png";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        
        // 파일명: "uuid_fileName{extension}" (고유한 UUID 사용)
        String uuid = UUID.randomUUID().toString();
        String newFilename = uuid + "_" + fileName + extension;
        File dest = new File(targetDir, newFilename);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.IMAGE_SAVE_ERROR);
        }
        // DB에 저장할 상대경로 (예: "member/profile/uuid_profile.png")
        return subDirectory + "/" + newFilename;
    }

    /**
     * 여러 파일을 저장하는 메서드
     * @param userId 파일명 생성 시 사용할 사용자 ID
     * @param subDirectory 저장할 하위 폴더 (예: "menu/images")
     * @param files 저장할 MultipartFile 리스트
     * @param fileName 파일명에 붙일 접두어 (예: "menu")
     * @return 저장된 파일들의 상대 경로 리스트
     */
    public List<String> saveMultipleFiles(Long userId, String subDirectory, String fileName, List<MultipartFile> files) {
        List<String> savedPaths = new ArrayList<>();
        if (files == null || files.isEmpty()) {
            return savedPaths;
        }

        File baseDir = new File(baseUploadDir).getAbsoluteFile();
        File targetDir = new File(baseDir, subDirectory);
        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file == null || file.isEmpty()) {
                continue; // 비어있는 파일은 건너뜀
            }
            String originalFilename = file.getOriginalFilename();
            String extension = ".png";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            }
            // 파일명: "uuid_fileName_{인덱스}{extension}" (고유한 UUID 사용)
            String uuid = UUID.randomUUID().toString();
            String newFilename = uuid + "_" + fileName + "_" + (i + 1) + extension;
            File dest = new File(targetDir, newFilename);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                throw new BusinessException(ErrorCode.IMAGE_SAVE_ERROR);
            }
            savedPaths.add(subDirectory + "/" + newFilename);
        }
        return savedPaths;
    }

    public void deleteFiles(List<String> filePaths) {
        if (filePaths == null || filePaths.isEmpty()) return;

        for (String filePath : filePaths) {
            File file = new File(baseUploadDir, filePath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

}
