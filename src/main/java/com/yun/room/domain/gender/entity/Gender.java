package com.yun.room.domain.gender.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Gender {
    @Id
    @Column(name = "gender_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    public Gender(String type) {
        this.type = type;
    }
}
