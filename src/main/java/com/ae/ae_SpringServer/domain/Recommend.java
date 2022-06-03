package com.ae.ae_SpringServer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "recommend")
@Getter
@Setter
public class Recommend {
    @Id
    @GeneratedValue
    @Column(name = "recommend_id")
    private Long id;

    private String text;
    private String server_date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public static Recommend createRecommend(String text, String date, User user) {
        Recommend recommend = new Recommend();
        recommend.setText(text);
        recommend.setServer_date(date);
        recommend.setUser(user);
        return recommend;
    }
}
