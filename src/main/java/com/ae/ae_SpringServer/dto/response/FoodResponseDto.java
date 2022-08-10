package com.ae.ae_SpringServer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class FoodResponseDto {
    @NotNull
    private String name;
    private int capacity;
    private double calory;
    private double carb;
    private double pro;
    private double fat;
}
