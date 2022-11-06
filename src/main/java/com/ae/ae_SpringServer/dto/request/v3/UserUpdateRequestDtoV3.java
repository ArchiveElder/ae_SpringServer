package com.ae.ae_SpringServer.dto.request.v3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequestDtoV3 {
    private String nickname;
    private int age;
    private String height;
    private String weight;
    private int activity;
}
