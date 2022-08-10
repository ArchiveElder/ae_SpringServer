package com.ae.ae_SpringServer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DateRecordDto {
    private int record_id;
    private String text;
    private String date;
    private String calory;
    private String carb;
    private String protein;
    private String fat;
    private String rdate;
    private String rtime;
    private Double amount;
}
