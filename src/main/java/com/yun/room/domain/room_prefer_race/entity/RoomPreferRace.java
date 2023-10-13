package com.yun.room.domain.room_prefer_race.entity;

import com.yun.room.domain.occupation.entity.Occupation;
import com.yun.room.domain.race.entity.Race;
import com.yun.room.domain.room.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class RoomPreferRace {
    @Id
    @Column(name = "room_prefer_race_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "race_id")
    private Race race;
}
