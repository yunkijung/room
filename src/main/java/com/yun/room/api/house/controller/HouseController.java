package com.yun.room.api.house.controller;

import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.repository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/houses")
public class HouseController {
    private final HouseRepository houseRepository;

    @PostMapping
    public ResponseEntity createHouse() {
        houseRepository.save(new House());
        return new ResponseEntity("success", HttpStatus.OK);
    }
}
