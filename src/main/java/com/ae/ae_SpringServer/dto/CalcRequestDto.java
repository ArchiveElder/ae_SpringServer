package com.ae.ae_SpringServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalcRequestDto {
    private String name;
    private int age;
    private int gender;
    private String height;
    private String weight;
    private int activity;
}
