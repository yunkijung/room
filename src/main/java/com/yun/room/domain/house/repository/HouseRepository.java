package com.yun.room.domain.house.repository;

import com.yun.room.domain.house.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}
