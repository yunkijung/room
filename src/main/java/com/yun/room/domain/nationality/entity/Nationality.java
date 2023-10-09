package com.yun.room.domain.nationality.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Nationality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nationalityId;

    private String name;

    public Nationality(String name) {
        this.name = name;
    }
}
