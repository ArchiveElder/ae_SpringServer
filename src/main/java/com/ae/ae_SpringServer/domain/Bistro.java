package com.ae.ae_SpringServer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
public class Bistro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Long id;
    private String category;
    private String name;

    private String wide;
    private String middle;

    @Column(name = "road_addr")
    private String rAddr;
    @Column(name = "lnm_addr")
    private String lAddr;
    @Column(name = "tel_no")
    private String tel;
    @Column(name = "menu_info")
    private String menu;
    @Column(name = "restaurant_la")
    private String la;
    @Column(name = "restaurant_lo")
    private String lo;
}
