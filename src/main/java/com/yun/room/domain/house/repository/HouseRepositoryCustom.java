package com.yun.room.domain.house.repository;

import com.yun.room.domain.house.entity.House;


import java.util.List;

public interface HouseRepositoryCustom {
    List<House> search(Double lng, Double lat, Double distance);
}
