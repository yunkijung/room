package com.yun.room.api.house.dto.get_all_houses;

import com.yun.room.domain.image.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

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
    private List<ImageDto> images;

    public GetAllHousesResponseDto(Long houseId, String title, String description, String street, String city, String country, String postalCode, Double latitude, Double longitude, List<ImageDto> images) {
        this.houseId = houseId;
        this.title = title;
        this.description = description;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.images = images;
    }
}

