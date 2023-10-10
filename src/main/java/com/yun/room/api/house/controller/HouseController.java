package com.yun.room.api.house.controller;

import com.yun.room.api.house.dto.CreateHouseDto;
import com.yun.room.domain.common.embedded.Address;
import com.yun.room.domain.component_service.house.service.HouseComponentService;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.repository.HouseRepository;
import com.yun.room.security.jwt.util.IfLogin;
import com.yun.room.security.jwt.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/houses")
public class HouseController {

    private final HouseComponentService houseComponentService;

    @PostMapping
    public ResponseEntity createHouse(@IfLogin LoginUserDto loginUserDto, @RequestBody @Valid CreateHouseDto createHouseDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Address address = new Address(
                createHouseDto.getStreet()
                , createHouseDto.getCity()
                , createHouseDto.getCountry()
                , createHouseDto.getPostalCode()
        );
        House house = new House(
                createHouseDto.getTitle()
                , createHouseDto.getDescription()
                , address
                , createHouseDto.getRoomCount()
                , createHouseDto.getWashroomCount()
                , createHouseDto.getToiletCount()
                , createHouseDto.getKitchenCount()
                , createHouseDto.getLivingRoomCount()
        );

        houseComponentService.createHouse(house, loginUserDto.getMemberId());

        return new ResponseEntity("success", HttpStatus.OK);
    }
}
