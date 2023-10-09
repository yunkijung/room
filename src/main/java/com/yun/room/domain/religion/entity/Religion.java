package com.yun.room.domain.religion.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Religion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long religionId;

    private String name;

    public Religion(String name) {
        this.name = name;
    }
}
