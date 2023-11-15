package com.yun.room.domain.component_service.room.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class RoomUpdateDto {
    private Long roomId;
    private String title;
    private String description;
    private Integer floor;
    private int price;
    private int minStay;
    private LocalDate availableDate;
    private List<Long> addedOffers;
    private List<Long> deletedOffers;
    private List<String> deletedFiles;
}
