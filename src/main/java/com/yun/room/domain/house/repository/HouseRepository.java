package com.yun.room.domain.house.repository;

import com.yun.room.domain.house.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long> {

    List<House> findByHost_Id(Long id);
}
