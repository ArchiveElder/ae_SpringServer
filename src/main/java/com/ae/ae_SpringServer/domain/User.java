package com.ae.ae_SpringServer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private int gender;
    private int age;
    private String height;
    private String weight;
    private String kakao;
    private String apple;
    private int icon;
    private int activity;

    @Column(name = "signup_date")
    private String date;

    @Column(name = "recommend_calory")
    private String rcal;
    @Column(name = "recommend_carb")
    private String rcarb;
    @Column(name = "recommend_pro")
    private String rpro;
    @Column(name = "recommend_fat")
    private String rfat;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Record> records = new ArrayList<>();

    public static User createUser(String kakao) {
        User user = new User();
        user.setKakao(kakao);
        return user;
    }
    public static User createAppleUser(String apple){
        User user = new User();
        user.setApple(apple);
        return user;
    }
}
