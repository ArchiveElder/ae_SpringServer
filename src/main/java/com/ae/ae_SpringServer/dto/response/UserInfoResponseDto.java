package com.ae.ae_SpringServer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoResponseDto {
    private String name;
    private int gender;
    private int age;
    private String height;
    private String weight;
    private int icon;
    private int activity;
}
