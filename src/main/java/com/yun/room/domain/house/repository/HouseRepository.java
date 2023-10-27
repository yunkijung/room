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
            "JOIN room r ON h.house_id = r.house_id " +
            "WHERE " +
            "  ST_Distance_Sphere(h.point, ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)) <= :distance " +
            "  AND (:type IS NULL OR :type = '' OR h.type = :type) " +
            "  AND (:minPrice IS NULL OR r.price >= :minPrice) " +
            "  AND (:maxPrice IS NULL OR r.price <= :maxPrice) " +
            "  AND (" +
            "    :hasBasement = true " +
            "    OR (" +
            "      :hasBasement = false " +
            "      AND (" +
            "        EXISTS (" +
            "          SELECT 1 " +
            "          FROM room r2 " +
            "          WHERE r2.house_id = h.house_id " +
            "          AND r2.floor <> 0" +
            "        ) " +
            "        OR NOT EXISTS (" +
            "          SELECT 1 " +
            "          FROM room r3 " +
            "          WHERE r3.house_id = h.house_id " +
            "        )" +
            "      )" +
            "    )" +
            "  ) " +
            "  AND (" +
            "    :availableDate IS NULL " +
            "    OR :availableDate = '' " +
            "    OR EXISTS (" +
            "      SELECT 1 " +
            "      FROM room r4 " +
            "      WHERE r4.house_id = h.house_id " +
            "      AND r4.available_date <= :availableDate" +
            "    )" +
            ")",
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






//    @Query(value = "SELECT * FROM house h " +
//            "WHERE " +
//            "  ST_Distance_Sphere(point, ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)) <= :distance " +
//            "  AND (:type IS NULL OR :type = '' OR h.type = :type)",
//            nativeQuery = true)
//    Page<House> searchByDistance(
//            @Param("lng") double lng,
//            @Param("lat") double lat,
//            @Param("distance") double distance,
//            @Param("type") String type,
//            Pageable pageable
//    );

//    @Query(value = "SELECT * FROM house h WHERE ST_Distance_Sphere(point, ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)) <= :distance", nativeQuery = true)
//    Page<House> searchByDistance(@Param("lng") double lng, @Param("lat") double lat, @Param("distance") double distance, @Param("type") String type , Pageable pageable);

//    @Query(value = "SELECT * FROM house h " +
//            "WHERE ST_Distance_Sphere(point, ST_GeomFromText(CONCAT('POINT(', :lat, ' ', :lng, ')'), 4326)) <= :distance " +
//            "AND (:availableDate IS NULL OR h.available_date = :availableDate) " +
//            "AND (:gender IS NULL OR h.gender = :gender)",
//            nativeQuery = true)
//    Page<House> searchByDistanceWithOptionalFilters(
//            @Param("lng") double lng,
//            @Param("lat") double lat,
//            @Param("distance") double distance,
//            @Param("availableDate") LocalDate availableDate,
//            @Param("gender") String gender,
//            Pageable pageable);

}
