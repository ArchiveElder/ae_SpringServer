package com.ae.ae_SpringServer.exception;

import lombok.Getter;

@Getter
public class AeException  extends RuntimeException{
    private final String code;
    private final String message;
    public AeException(CodeAndMessage errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();

    }
}
