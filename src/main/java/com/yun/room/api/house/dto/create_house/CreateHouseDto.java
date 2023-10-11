package com.yun.room.api.house.dto.create_house;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CreateHouseDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    @NotBlank
    private String postalCode;
    @NotNull
    @Min(value=1, message="Minimum count is 1")
    private int roomCount;
    @NotNull
    @Min(value=1, message="Minimum count is 1")
    private int washroomCount;
    @NotNull
    @Min(value=1, message="Minimum count is 1")
    private int toiletCount;
    @NotNull
    @Min(value=0, message="Minimum count is 1")
    private int kitchenCount;
    @NotNull
    @Min(value=0, message="Minimum count is 1")
    private int livingRoomCount;
}
