package com.ae.ae_SpringServer.dto.response;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppleLoginResponse {
    @NotNull
    private Long userId;
    //private String accessToken;
    //private String refreshToken;
    private String accessJwtToken;
    private boolean isSignup; // 온보딩 띄워야 하는 여부 (true가 띄워야함)


}
