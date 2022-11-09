package com.ae.ae_SpringServer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "suggestion")
@Getter
@Setter
@NoArgsConstructor
public class Suggestion {
    @Id
    private Long id;
    private Long foodId;
    private String foodUrl;
    private String foodName;
    private Long problemId;

}
