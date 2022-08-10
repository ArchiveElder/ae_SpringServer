package com.ae.ae_SpringServer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
@AllArgsConstructor
public class CreateBookmarkResponseDto {
    @NotNull
    private int id;
}
