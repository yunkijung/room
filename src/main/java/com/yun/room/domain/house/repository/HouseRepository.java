package com.yun.room.domain.house.repository;

import com.yun.room.domain.house.entity.House;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long>, HouseRepositoryCustom {

    List<House> findByHost_Id(Long id);

    @Query(value = "SELECT DISTINCT h.* " +
            "FROM house h " +
            "WHERE " +
            "  ST_Distance_Sphere(h.point, ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)) <= :distance " +
            "  AND (:type IS NULL OR :type = '' OR h.type = :type) " +
            "  AND EXISTS (" +
            "      SELECT 1 FROM room r " +
            "      WHERE r.house_id = h.house_id " +
            "      AND r.is_on = true" +
            "  ) " +
            "  AND (:minPrice IS NULL OR EXISTS (" +
            "      SELECT 1 FROM room r2 " +
            "      WHERE r2.house_id = h.house_id AND r2.price >= :minPrice AND r2.is_on = true" +
            "  )) " +
            "  AND (:maxPrice IS NULL OR EXISTS (" +
            "      SELECT 1 FROM room r3 " +
            "      WHERE r3.house_id = h.house_id AND r3.price <= :maxPrice AND r3.is_on = true" +
            "  )) " +
            "  AND (" +
            "    :hasBasement = true " +
            "    OR (:hasBasement = false AND EXISTS (" +
            "        SELECT 1 FROM room r4 " +
            "        WHERE r4.house_id = h.house_id AND r4.floor <> 0 AND r4.is_on = true" +
            "    ))" +
            "  ) " +
            "  AND (:availableDate IS NULL OR :availableDate = '' OR EXISTS (" +
            "      SELECT 1 FROM room r5 " +
            "      WHERE r5.house_id = h.house_id AND r5.available_date <= :availableDate AND r5.is_on = true" +
            "  ))",
            nativeQuery = true)
    Page<House> searchByDistance(
            @Param("lng") double lng,
            @Param("lat") double lat,
            @Param("distance") double distance,
            @Param("type") String type,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            @Param("hasBasement") Boolean hasBasement,
            @Param("availableDate") LocalDate availableDate,
            Pageable pageable
    );


}
