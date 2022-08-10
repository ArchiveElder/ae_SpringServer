package com.ae.ae_SpringServer.dto.response;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DateRecordResponseDto {
    private int totalCalory;
    private int totalCarb;
    private int totalPro;
    private int totalFat;
    private int recommCalory;
    private int recommCarb;
    private int recommPro;
    private int recommFat;
    private List<RecordsDto> records;
}
