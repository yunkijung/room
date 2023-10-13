package com.yun.room.domain.religion.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Religion {

    @Id
    @Column(name = "religion_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Religion(String name) {
        this.name = name;
    }
}
