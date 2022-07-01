package com.ae.ae_SpringServer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDto {
    private int age;
    private int icon;
    private String height;
    private String weight;
    private int activity;
}
