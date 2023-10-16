package com.yun.room.api.room.dto.get_room;

import com.yun.room.api.house.dto.get_all_houses.ImageDto;
import com.yun.room.api.house.dto.get_all_houses.OfferDto;
import com.yun.room.api.house.dto.get_all_houses.RoomDto;
import com.yun.room.api.house.dto.get_all_houses.RuleDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class HouseDto {
    private Long houseId;
    private String title;
    private String description;
    private String street;
    private String city;
    private String country;
    private String postalCode;

    private int roomCount;
    private int washroomCount;
    private int toiletCount;
    private int kitchenCount;
    private int livingRoomCount;

    private Double latitude;
    private Double longitude;
    private List<ImageDto> images;
    private List<OfferDto> houseOffers;
    private List<RuleDto> houseRules;
    private List<RoomDto> rooms;
}