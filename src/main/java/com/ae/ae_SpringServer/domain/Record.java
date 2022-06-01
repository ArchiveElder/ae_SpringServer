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
    private String calory;
    private String carb;
    private String protein;
    private String fat;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public static Record createRecord(String text, String date, String calory, String carb, String protein, String fat, User user) {
        Record record = new Record();
        record.setText(text);
        record.setServer_date(date);
        record.setCalory(calory);
        record.setCarb(carb);
        record.setProtein(protein);
        record.setFat(fat);
        record.setUser(user);
        return record;
    }
}
