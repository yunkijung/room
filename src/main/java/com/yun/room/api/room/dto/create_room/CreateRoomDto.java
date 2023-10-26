package com.yun.room.api.room.dto.create_room;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateRoomDto {
    private String title;
    private String description;
    private Integer floor;
    private int price;
    private int minStay;
    private Boolean isOn;
    private LocalDate availableDate;
    private Long houseId;
    private List<Long> roomOffers;
}
