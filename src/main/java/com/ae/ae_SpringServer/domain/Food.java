package com.ae.ae_SpringServer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Food {
    @Id
    @GeneratedValue
    @Column(name = "food_id")
    private Long id;

    @Column(name = "food_type")
    private String name;

    private int capacity;

    @Column(name = "food_calory")
    private Double calory;

    @Column(name = "food_carb")
    private Double carb;

    @Column(name = "food_pro")
    private Double pro;

    @Column(name = "food_fat")
    private Double fat;
}
