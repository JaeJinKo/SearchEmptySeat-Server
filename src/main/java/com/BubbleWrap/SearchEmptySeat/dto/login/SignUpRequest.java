package com.BubbleWrap.SearchEmptySeat.dto.login;

import com.BubbleWrap.SearchEmptySeat.model.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    @Email
    @NotBlank(message = "이메일을 입력해야 합니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해야 합니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6~20자 사이여야 합니다.")
    private String password;

    @NotBlank(message = "이름을 입력해야 합니다.")
    private String name;

    private String phone;
    private String location;

    @NotNull(message = "유저 타입을 입력해야 합니다.")
    private UserType userType;
}