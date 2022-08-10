package com.ae.ae_SpringServer.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DateRecordRequestDto {
    @NotNull
    private String date;
}
