package com.yun.room.domain.house.service;

import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.repository.HouseRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class HouseService {
    private final HouseRepository houseRepository;

    @Transactional
    public House save(House house) {
        return houseRepository.save(house);
    }

    @Transactional
    public List<House> findAll() {
        return houseRepository.findAll();
    }

    @Transactional
    public House findById(Long id) {
        return houseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not exist."));
    }

    @Transactional
    public List<House> findAllByHost(Long id) {
        return houseRepository.findByHost_Id(id);
    }

    @Transactional
    public List<House> search(Double lng, Double lat , Double distance) {
        log.info("lng : {}", lng);
        log.info("lat : {}", lat);
        log.info("distance : {}", distance);

        return houseRepository.search(lng, lat, distance);
    }

    @Transactional
    public Page<House> searchByDistance(Double lng, Double lat, Double distance, Pageable pageable) {

        return houseRepository.searchByDistance(lng, lat, distance, pageable);
    }
}
