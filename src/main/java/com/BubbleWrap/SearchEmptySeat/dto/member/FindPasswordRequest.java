package com.BubbleWrap.SearchEmptySeat.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPasswordRequest {
    @Email(message = "올바른 이메일 주소를 입력하세요.")
    @NotBlank(message = "이메일을 입력하세요.")
    private String email;
}
