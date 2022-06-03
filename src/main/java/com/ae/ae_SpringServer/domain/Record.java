package com.ae.ae_SpringServer.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "record")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Record {
    @Id
    @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    //private String image_url;
    private String text;
    private String server_date;
    private String cal;
    private String carb;
    private String protein;
    private String fat;

    @Column(name = "record_date")
    private String date;

    @Column(name = "record_time")
    private String time;

    @Column(name = "food_amount")
    private Double amount;

    private int meal;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_user_id")
    private User user;


    public static Record createRecord(String text, String date, String cal, String carb, String protein, String fat, String rdate, String rtime,
                                      Double amount, int meal, User user) {
        Record record = new Record();
        record.setText(text);
        record.setServer_date(date);
        record.setCal(cal);
        record.setCarb(carb);
        record.setProtein(protein);
        record.setFat(fat);
        record.setDate(rdate);
        record.setTime(rtime);
        record.setAmount(amount);
        record.setUser(user);
        record.setMeal(meal);
        return record;
    }
}
