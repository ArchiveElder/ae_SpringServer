package com.ae.ae_SpringServer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bistro_v2")
@Getter
@Setter
@NoArgsConstructor
public class BistroV2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Column(name = "rstrnt_la")
    private String la;
    @Column(name = "rstrnt_lo")
    private String lo;

    private String bistroUrl;

    @Column(name = "main_category")
    private String mainCategory;
    @Column(name = "middle_category")
    private String middleCategory;

}
