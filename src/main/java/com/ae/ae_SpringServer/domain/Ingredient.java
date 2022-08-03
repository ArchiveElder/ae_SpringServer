package com.ae.ae_SpringServer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Ingredient {
    @Id @GeneratedValue
    @Column(name="id")
    private Long id;

    @NotNull
    private String name;
}
