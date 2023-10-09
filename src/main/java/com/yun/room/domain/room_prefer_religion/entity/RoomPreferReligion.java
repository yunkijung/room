package com.yun.room.domain.room_prefer_religion.entity;

import com.yun.room.domain.race.entity.Race;
import com.yun.room.domain.religion.entity.Religion;
import com.yun.room.domain.room.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class RoomPreferReligion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomPreferReligionId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "religion_id")
    private Religion religion;
}
