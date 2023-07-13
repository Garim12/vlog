package com.sparta.vlog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;
    @Pattern(regexp = "^[A-Za-z0-9!@#$%^&*()_+{}:\"<>?,.\\\\/]{8,15}$")
    private String password;
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일의 형식이 올바르지 않습니다. 다시 작성해주세요.")
    @NotBlank
    private String email;
    private boolean admin = false;
    private String adminToken = "";
}