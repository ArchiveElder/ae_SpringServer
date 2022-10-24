package com.ae.ae_SpringServer.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DetailRecordRequestDto {
    @NotNull
    private int record_id;
    private int meal;
}
