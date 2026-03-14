package com.example.dto;

import lombok.Getter;

public class ResetPasswordRequestDTO {
    @Getter
    private String token;
    @Getter
    private String newPassword;
    @Getter
    private String email;
}
