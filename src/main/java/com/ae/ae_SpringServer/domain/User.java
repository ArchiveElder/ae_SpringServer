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
    private String name;
    private int gender;
    private int age;
    private String height;
    private String weight;
    private String kakao;
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

    @ManyToMany
    @JoinTable(name = "bookmark",
    joinColumns = @JoinColumn(name = "user_user_id"),
    inverseJoinColumns = @JoinColumn(name = "restaurant_restaurant_id"))
    private List<Restaurant> restaurants = new ArrayList<>();


    public static User createUser(String kakao) {
        User user = new User();
        user.setKakao(kakao);
        return user;
    }
}
