package com.yun.room.api.house.controller;

import com.google.maps.model.LatLng;
import com.yun.room.api.external_service.S3UploadService;
import com.yun.room.api.external_service.S3Uploader;
import com.yun.room.api.house.dto.create_house.CreateHouseDto;
import com.yun.room.api.house.dto.get_all_houses.GetAllHousesResponseDto;
import com.yun.room.api.external_service.GeocodingAPIService;
import com.yun.room.api.house.dto.get_all_houses.ImageDto;
import com.yun.room.domain.common.embedded.Address;
import com.yun.room.domain.component_service.house.service.HouseComponentService;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.service.HouseService;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.security.jwt.util.IfLogin;
import com.yun.room.security.jwt.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/houses")
public class HouseController {

    private final HouseComponentService houseComponentService;
    private final GeocodingAPIService geocodingApiService;
    private final S3Uploader s3Uploader;
    private final S3UploadService s3UploadService;
    private final HouseService houseService;

//    @PostMapping("/{userId}/image")
//    public ResponseEntity updateUserImage(@RequestPart("files") List<MultipartFile> files) {
//        try {
//            s3Uploader.uploadFiles(multipartFile, "static");
//        } catch (Exception e) { return new ResponseEntity(HttpStatus.BAD_REQUEST); }
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//        List<Image> result = new ArrayList<>();
//        try{
//            result = s3UploadService.saveFiles(files);
//        } catch (Exception e) {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity(result, HttpStatus.OK);
//    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createHouse(@IfLogin LoginUserDto loginUserDto, @RequestPart @Valid CreateHouseDto createHouseDto, @RequestPart @Valid Optional<List<MultipartFile>> files, BindingResult bindingResult) {
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

        List<Image> images = new ArrayList<>();
        try{
            if (!files.isEmpty()) {
                images = s3UploadService.saveFiles(files.get());
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        houseComponentService.createHouse(house, loginUserDto.getMemberId(), images);

        return new ResponseEntity("success", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllHouses() {
        List<House> allHouses = houseService.findAll();
        List<GetAllHousesResponseDto> responseDtos = new ArrayList<>();
        for (House house : allHouses) {
            List<Image> images = house.getImages();
            List<ImageDto> imageDtos = new ArrayList<>();
            for (Image image : images) {
                imageDtos.add(new ImageDto(image.getOriginFilename(), image.getFileUrl()));
            }
            responseDtos.add(new GetAllHousesResponseDto(
                    house.getHouseId()
                    , house.getTitle()
                    , house.getDescription()
                    , house.getAddress().getStreet()
                    , house.getAddress().getCity()
                    , house.getAddress().getCountry()
                    , house.getAddress().getPostalCode()
                    , house.getPoint().getX()
                    , house.getPoint().getY()
                    , imageDtos
            ));
        }
        return new ResponseEntity(responseDtos, HttpStatus.OK);
    }
}
