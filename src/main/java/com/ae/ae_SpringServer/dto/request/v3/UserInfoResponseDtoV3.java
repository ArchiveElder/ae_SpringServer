package com.ae.ae_SpringServer.dto.request.v3;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoResponseDtoV3 {
    private String nickname;
    private int gender;
    private int age;
    private String height;
    private String weight;
    private int icon;
    private int activity;
}
