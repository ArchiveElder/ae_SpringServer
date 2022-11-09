package com.ae.ae_SpringServer.exception;

import com.ae.ae_SpringServer.dto.response.ApiResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse extends ApiResponse {
    @Builder
    public ErrorResponse (String code, String message) {
        super(code, message);
    }

}