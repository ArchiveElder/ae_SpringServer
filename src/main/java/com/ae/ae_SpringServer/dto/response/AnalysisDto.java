package com.ae.ae_SpringServer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalysisDto {
    private String date;
    private int totalCal;
}
