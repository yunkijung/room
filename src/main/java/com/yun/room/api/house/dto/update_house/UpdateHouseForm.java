package com.yun.room.api.house.dto.update_house;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UpdateHouseForm {
    private String title;
    private String description;
    private String type;
    private String street;

    private String city;

    private String country;

    private String postalCode;

    @Min(value=1, message="Minimum count is 1")
    private int roomCount;

    @Min(value=1, message="Minimum count is 1")
    private int washroomCount;

    @Min(value=1, message="Minimum count is 1")
    private int toiletCount;

    @Min(value=0, message="Minimum count is 1")
    private int kitchenCount;

    @Min(value=0, message="Minimum count is 1")
    private int livingRoomCount;

    private List<Long> addedRules;
    private List<Long> deletedRules;
    private List<Long> addedOffers;
    private List<Long> deletedOffers;
    private List<String> deletedFiles;
}
