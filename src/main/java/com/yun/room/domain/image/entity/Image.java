package com.yun.room.domain.image.entity;

import com.yun.room.domain.common.auditor.AuditorEntity;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.room.entity.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Image extends AuditorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private Integer sequence;
    private String originFileName;
    private String uniqueFileName;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


}
