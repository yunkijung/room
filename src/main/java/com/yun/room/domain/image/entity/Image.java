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
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String originFilename;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Image(String originFilename, String fileUrl) {
        this.originFilename = originFilename;
        this.fileUrl = fileUrl;
    }

    public void updateHouse(House house) {
        this.house = house;
    }
    public void updateRoom(Room room) {
        this.room = room;
    }
}
