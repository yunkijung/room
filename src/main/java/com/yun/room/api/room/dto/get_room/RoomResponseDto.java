package com.yun.room.api.room.dto.get_room;

import com.yun.room.api.house.dto.get_all_houses.ImageDto;

import com.yun.room.api.room.dto.get_options.OfferRDto;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.room.entity.Room;
import com.yun.room.domain.room_offer_r.entity.RoomOfferR;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class RoomResponseDto {
    private Long roomId;
    private String title;
    private String description;
    private int price;
    private int minStay;
    private Boolean isOn;
    private LocalDate availableDate;
    private List<ImageDto> images;
    private List<OfferRDto> roomOffers;
    private HouseDto house;

    public RoomResponseDto(Room room) {
        List<Image> roomImages = room.getImages();
        List<ImageDto> roomImageDtos = new ArrayList<>();
        for (Image roomImage : roomImages) {
            roomImageDtos.add(new ImageDto(roomImage));
        }

        List<RoomOfferR> roomOfferRList = room.getRoomOfferRList();
        List<OfferRDto> roomOfferRDtos = new ArrayList<>();
        for (RoomOfferR roomOfferR : roomOfferRList) {
            roomOfferRDtos.add(new OfferRDto(roomOfferR.getOfferR()));
        }

        ///////
        House house = room.getHouse();
        HouseDto houseDto = new HouseDto(house);

        this.roomId = room.getId();
        this.title =  room.getTitle();
        this.description =  room.getDescription();
        this.price =  room.getPrice();
        this.minStay =  room.getMinStay();
        this.isOn =  room.getIsOn();
        this.availableDate =  room.getAvailableDate();
        this.images =  roomImageDtos;
        this.roomOffers =  roomOfferRDtos;
        this.house =  houseDto;
    }
}

