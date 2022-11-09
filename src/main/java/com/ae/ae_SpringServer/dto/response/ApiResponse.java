package com.ae.ae_SpringServer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {
    private String code;
    private String message;
}