package com.yun.room.api.room.dto.get_room;

import com.yun.room.api.house.dto.get_all_houses.ImageDto;

import com.yun.room.api.room.dto.get_options.OfferRDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
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
}

