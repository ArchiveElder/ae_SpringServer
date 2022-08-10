package com.ae.ae_SpringServer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
public class LoginResponseDto {
    @NotNull
    private Long userId;
    @NotNull
    private String token;
    @NotNull
    private boolean isSignup; // 온보딩 띄워야 하는 여부 (true가 띄워야함)
}

