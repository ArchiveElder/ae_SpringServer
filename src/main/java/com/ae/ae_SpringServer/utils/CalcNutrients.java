package com.ae.ae_SpringServer.utils;

import com.ae.ae_SpringServer.dto.CalcNutrientDtos;
import com.ae.ae_SpringServer.dto.request.CalcRequestDto;


public class CalcNutrients {
    public static CalcNutrientDtos calcNutrientDtos(CalcRequestDto dto) {
        int gender = dto.getGender();
        int age = dto.getAge();
        double rCal = (Double.parseDouble(dto.getHeight()) - 100) * 0.9 * dto.getActivity();

        int pro = 0;
        double fat, lino, alp, dha;
        lino = alp = dha = 0D;
        // 0이 남성, 1이 여성
        if (gender == 0) {
            if (age >= 6 && age <= 8) {
                pro = 35;
                lino = 9.0;
                alp = 1.1;
                dha = 0.2;
            } else if (age >= 9 && age <= 11) {
                pro = 50;
                lino = 9.5;
                alp = 1.3;
                dha = 0.22;
            } else if (age >= 12 && age <= 14) {
                pro = 60;
                lino = 12.0;
                alp = 1.5;
                dha = 0.23;
            } else if (age >= 15 && age <= 18) {
                pro = 65;
                lino = 14.0;
                alp = 1.7;
                dha = 0.23;
            } else if (age >= 19 && age <= 29) {
                pro = 65;
                lino = 13.0;
                alp = 1.6;
                dha = 0.21;
            } else if (age >= 30 && age <= 49) {
                pro = 65;
                lino = 11.5;
                alp = 1.4;
                dha = 0.4;
            } else if (age >= 50 && age <= 64) {
                pro = 60;
                lino = 9.0;
                alp = 1.4;
                dha = 0.5;
            } else if (age >= 65 && age <= 74) {
                pro = 60;
                lino = 7.0;
                alp = 1.2;
                dha = 0.31;
            } else if (age >= 75) {
                pro = 60;
                lino = 5.0;
                alp = 0.9;
                dha = 0.28;
            }
        } else if (gender == 1) {
            if (age >= 6 && age <= 8) {
                pro = 35;
                lino = 7.0;
                alp = 0.8;
                dha = 0.2;
            } else if (age >= 9 && age <= 11) {
                pro = 45;
                lino = 9.0;
                alp = 1.1;
                dha = 0.15;
            } else if (age >= 12 && age <= 14) {
                pro = 55;
                lino = 9.0;
                alp = 1.2;
                dha = 0.21;
            } else if (age >= 15 && age <= 18) {
                pro = 55;
                lino = 10.0;
                alp = 1.1;
                dha = 0.1;
            } else if (age >= 19 && age <= 29) {
                pro = 55;
                lino = 10.0;
                alp = 1.2;
                dha = 0.15;
            } else if (age >= 30 && age <= 49) {
                pro = 50;
                lino = 8.5;
                alp = 1.2;
                dha = 0.26;
            } else if (age >= 50 && age <= 64) {
                pro = 50;
                lino = 7.0;
                alp = 1.2;
                dha = 0.24;
            } else if (age >= 65 && age <= 74) {
                pro = 50;
                lino = 4.5;
                alp = 1.0;
                dha = 0.15;
            } else if (age >= 75) {
                pro = 50;
                lino = 3.0;
                alp = 0.4;
                dha = 0.14;
            }
        }
        fat = lino + alp + dha;
        fat = Math.round((fat * 100) / 100.0);
        return new CalcNutrientDtos(Double.toString(rCal), "130", Double.toString(pro), Double.toString(fat));
    }

}
