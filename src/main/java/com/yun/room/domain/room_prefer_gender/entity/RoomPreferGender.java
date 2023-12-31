package com.yun.room.domain.room_prefer_gender.entity;

import com.yun.room.domain.gender.entity.Gender;
import com.yun.room.domain.room.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class RoomPreferGender {
    @Id
    @Column(name = "room_prefer_gender_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;
}
