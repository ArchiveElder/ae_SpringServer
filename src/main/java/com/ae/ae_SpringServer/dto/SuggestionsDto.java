package com.ae.ae_SpringServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuggestionsDto {
    private Long problemId;
    private String foodUrl;
    private String foodName;
}
