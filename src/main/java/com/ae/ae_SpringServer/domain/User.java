package com.ae.ae_SpringServer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;
    private int icon;
    private int gender;
    private String height;
    private String weight;
    private int activity;

    @Column(name = "user_calory")
    private int ucalory;

    @Column(name = "user_carb")
    private int ucarb;

    @Column(name = "user_pro")
    private int upro;

    @Column(name = "user_fat")
    private int ufat;

    @Column(name = "remain_calory")
    private int rcalory;

    @Column(name = "remain_carb")
    private int rcarb;

    @Column(name = "remain_pro")
    private int rpro;

    @Column(name = "remain_fat")
    private int rfat;

    @Column(name = "signup_date")
    private String date;

    @Column(name = "recommend_calory")
    private int rec_cal;



    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Recommend> recommends = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Record> records = new ArrayList<>();
}
