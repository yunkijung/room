package com.yun.room.domain.occupation.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Occupation {

    @Id
    @Column(name = "occupation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Occupation(String name) {
        this.name = name;
    }
}
