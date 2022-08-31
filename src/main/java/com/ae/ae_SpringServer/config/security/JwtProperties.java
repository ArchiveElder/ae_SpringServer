package com.ae.ae_SpringServer.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {
    public static String key1;
    public static String key2;
    public static String key3;

    //@Value("${APPLE.TEAM.ID}")
    public void setKey1(String value) {
        key1 = "static/AuthKey_WYJQ5FU845.p8";
    }

   // @Value("${APPLE.KEY.ID}")
    public void setKey2(String value) {
        key2 = "7B7X32G7J9";
    }

    //@Value("${APPLE.KEY.PATH}")
    public void setKey3(String value) {
        key3 = "WYJQ5FU845";
    }

}
