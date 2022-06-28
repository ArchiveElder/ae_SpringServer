package com.ae.ae_SpringServer.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Data
@AllArgsConstructor
public class DateAnalysisDto {
    private String date;
    private String sumCal;
    private String sumCarb;
    private String sumPro;
    private String sumFat;
}
