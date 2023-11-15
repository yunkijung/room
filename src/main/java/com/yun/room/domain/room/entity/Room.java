package com.yun.room.domain.room.entity;

import com.yun.room.domain.common.auditor.AuditorEntity;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.room_offer_r.entity.RoomOfferR;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Room extends AuditorEntity {
    @Id
    @Column(name="room_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Integer floor;
    private Integer price;
    private Integer minStay;
    private Boolean isOn;
    private LocalDate availableDate;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<RoomOfferR> roomOfferRList;

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Image> images;

    public Room(String title, String description, Integer floor, Integer price, Integer minStay, Boolean isOn, LocalDate availableDate) {
        this.title = title;
        this.description = description;
        this.floor = floor;
        this.price = price;
        this.minStay = minStay;
        this.isOn = isOn;
        this.availableDate = availableDate;
    }

    public void updateHouse(House house) {
        this.house = house;
        house.getRooms().add(this);
    }

    public void updateImages(List<Image> images) {
        this.images = images;
        for (Image image : images) {
            image.updateRoom(this);
//            image.updateHouse(this.house);
        }
    }



    public void updateIsOn(Boolean isOn) {
        this.isOn = isOn;
    }

    public void updateRoomInfo(String title, String description, Integer floor, Integer price, Integer minStay, LocalDate availableDate) {
        if(title != null){
            this.title = title;
        }
        if(description != null){
            this.description = description;
        }
        if(floor != null){
            this.floor = floor;
        }
        if(price != null){
            this.price = price;
        }
        if(minStay != null){
            this.minStay = minStay;
        }
        if(availableDate != null){
            this.availableDate = availableDate;
        }

    }

    public void updateRoomOfferRList(List<RoomOfferR> roomOfferRList) {
        this.roomOfferRList = roomOfferRList;
    }
}
