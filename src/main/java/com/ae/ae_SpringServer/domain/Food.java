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

    @Column(name = "is_vegetarian")
    private int vegetarian;

    @Column(name = "food_type")
    private String name;

    private int capacity;

    @Column(name = "food_calory")
    private Long calory;

    @Column(name = "food_carb")
    private Long carb;

    @Column(name = "food_pro")
    private Long pro;

    @Column(name = "food_fat")
    private Long fat;
}
