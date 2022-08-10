package com.ae.ae_SpringServer.dto.response;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DateRecordRequestDto {
    @NotNull
    private String date;
}
