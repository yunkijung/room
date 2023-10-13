package com.yun.room.domain.room_prefer_occupation.entity;

import com.yun.room.domain.nationality.entity.Nationality;
import com.yun.room.domain.occupation.entity.Occupation;
import com.yun.room.domain.room.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class RoomPreferOccupation {
    @Id
    @Column(name = "room_prefer_occupation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "occupation_id")
    private Occupation occupation;
}
