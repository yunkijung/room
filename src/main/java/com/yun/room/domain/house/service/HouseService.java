package com.yun.room.domain.house.service;

import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.repository.HouseRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
