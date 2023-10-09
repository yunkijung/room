package com.yun.room.domain.room_prefer_nationality.entity;

import com.yun.room.domain.gender.entity.Gender;
import com.yun.room.domain.nationality.entity.Nationality;
import com.yun.room.domain.room.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class RoomPreferNationality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomPreferNationalityId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "nationality_id")
    private Nationality nationality;
}
