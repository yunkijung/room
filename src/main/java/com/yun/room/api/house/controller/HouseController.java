package com.yun.room.api.house.controller;

import com.google.maps.model.LatLng;
import com.yun.room.api.house.dto.create_house.CreateHouseDto;
import com.yun.room.api.house.dto.get_all_houses.GetAllHousesResponseDto;
import com.yun.room.api.house.service.GeocodingAPIService;
import com.yun.room.domain.common.embedded.Address;
import com.yun.room.domain.component_service.house.service.HouseComponentService;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.service.HouseService;
import com.yun.room.security.jwt.util.IfLogin;
import com.yun.room.security.jwt.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/houses")
public class HouseController {

    private final HouseComponentService houseComponentService;
    private final GeocodingAPIService geocodingApiService;
    private final HouseService houseService;

    @PostMapping
    public ResponseEntity createHouse(@IfLogin LoginUserDto loginUserDto, @RequestBody @Valid CreateHouseDto createHouseDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        log.info("location = @@@@@@@@@@@@@@@@@@@@@@@@@@@");
        Point point = null;
        try {
            LatLng location = geocodingApiService.getLatLngFromAddress(
                    createHouseDto.getStreet() + ", "
                    + createHouseDto.getCity() + ", "
                    + createHouseDto.getCountry()
            );
            log.info("location = {}, {}", location.lat, location.lng);
            double latitude = location.lat;
            double longitude = location.lng;
            point = new Point(latitude, longitude);
            // Use latitude and longitude as needed.
        } catch (Exception e) {
            // Handle the exception
            log.error("GeocodingAPI Error : ", e);
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
                , point
                , createHouseDto.getRoomCount()
                , createHouseDto.getWashroomCount()
                , createHouseDto.getToiletCount()
                , createHouseDto.getKitchenCount()
                , createHouseDto.getLivingRoomCount()
        );

        houseComponentService.createHouse(house, loginUserDto.getMemberId());

        return new ResponseEntity("success", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllHouses() {
        List<House> allHouses = houseService.getAllHouses();
        List<GetAllHousesResponseDto> responseDtos = new ArrayList<>();
        for (House house : allHouses) {
            responseDtos.add(new GetAllHousesResponseDto(
                    house.getTitle()
                    , house.getDescription()
                    , house.getAddress().getStreet()
                    , house.getAddress().getCity()
                    , house.getAddress().getCountry()
                    , house.getAddress().getPostalCode()
                    , house.getPoint().getX()
                    , house.getPoint().getY()
            ));
        }
        return new ResponseEntity(responseDtos, HttpStatus.OK);
    }
}
