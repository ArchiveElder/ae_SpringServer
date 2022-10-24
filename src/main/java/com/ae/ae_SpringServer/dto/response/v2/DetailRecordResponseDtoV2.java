package com.ae.ae_SpringServer.dto.response.v2;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetailRecordResponseDtoV2 {
    private String text;
    private String cal;
    private String carb;
    private String protein;
    private String fat;
    private String image_url;
    private String date;
    private String time;
    private Double amount;
    private int meal;
}
