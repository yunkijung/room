package com.yun.room.domain.component_service.house.dto;

import com.yun.room.domain.common.embedded.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import javax.validation.constraints.Min;
import java.util.List;

@Data
@AllArgsConstructor
public class HouseUpdateDto {
    private Long houseId;
    private String title;
    private String description;
    private String type;
    private Address address;
    private Point point;

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
