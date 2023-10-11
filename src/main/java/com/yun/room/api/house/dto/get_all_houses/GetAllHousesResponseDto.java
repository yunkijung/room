package com.yun.room.api.house.dto.get_all_houses;

import lombok.Data;

@Data
public class GetAllHousesResponseDto {
    private Long houseId;
    private String title;
    private String description;
    private String street;
    private String city;
    private String country;
    private String postalCode;
    private Double latitude;
    private Double longitude;

    public GetAllHousesResponseDto(Long houseId, String title, String description, String street, String city, String country, String postalCode, Double latitude, Double longitude) {
        this.houseId = houseId;
        this.title = title;
        this.description = description;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
