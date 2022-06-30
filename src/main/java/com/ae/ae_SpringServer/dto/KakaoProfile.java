package com.ae.ae_SpringServer.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
public class KakaoProfile {
    private Long id;
    private KakaoAccount kakao_account;

    @Getter
    @ToString
    public static class KakaoAccount {
        private String email;
    }
}
