package com.yun.room.domain.nationality.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Nationality {
    @Id
    @Column(name = "nationality_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Nationality(String name) {
        this.name = name;
    }
}
