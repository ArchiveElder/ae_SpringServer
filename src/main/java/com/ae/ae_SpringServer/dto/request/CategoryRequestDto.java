package com.ae.ae_SpringServer.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class CategoryRequestDto {
    @NotNull
    private String wide;
    @NotNull
    private String middle;
}
