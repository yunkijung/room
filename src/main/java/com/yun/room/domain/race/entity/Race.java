package com.yun.room.domain.race.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Race {

    @Id
    @Column(name = "race_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    public Race(String type) {
        this.type = type;
    }
}
