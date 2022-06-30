package com.ae.ae_SpringServer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    private String kakao;

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


    public static User createUser(String kakao) {
        User user = new User();
        user.setKakao(kakao);
        return user;
    }
}
