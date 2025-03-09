package com.BubbleWrap.SearchEmptySeat.dto.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MyInfoUpdateRequest {
    private String email;
    private String name;
    private String password;
    private String location;
    private MultipartFile imageFile; // 단일 이미지
}
