package com.ae.ae_SpringServer.jpql;

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
    private Double sumCal;
    private Double sumCarb;
    private Double sumPro;
    private Double sumFat;
}
