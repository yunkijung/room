package com.yun.room.api.house.dto.search_houses;

import lombok.Data;

@Data
public class SearchHousesDto {
    private Double lng;
    private Double lat;
    private Double distance;
}
