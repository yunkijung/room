package com.yun.room.api.house.dto.search_houses;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class SearchHousesDto {
    private Double lng;
    private Double lat;
    private Double distance;
    private Integer pageNumber;
    private Integer pageSize;
    private String type;
    private Integer minPrice;
    private Integer maxPrice;
    private Boolean hasBasement;
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Specify the date format
    private LocalDate availableDate;
}
