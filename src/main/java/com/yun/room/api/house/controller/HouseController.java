package com.yun.room.api.house.controller;

import com.google.maps.model.LatLng;
import com.yun.room.api.external_service.S3UploadService;

import com.yun.room.api.house.dto.create_house.CreateHouseDto;

import com.yun.room.api.external_service.GeocodingAPIService;
import com.yun.room.api.house.dto.get_options.OfferHDto;
import com.yun.room.api.house.dto.get_options.RuleHDto;
import com.yun.room.api.house.dto.search_houses.HouseResponseDto;
import com.yun.room.api.house.dto.search_houses.SearchHousesDto;
import com.yun.room.api.house.dto.update_house.UpdateHouseForm;
import com.yun.room.api.room.dto.get_options.OfferRDto;
import com.yun.room.domain.common.embedded.Address;
import com.yun.room.domain.component_service.house.dto.HouseUpdateDto;
import com.yun.room.domain.component_service.house.service.HouseComponentService;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.service.HouseService;
import com.yun.room.domain.house_offer_h.entity.HouseOfferH;
import com.yun.room.domain.house_rule_h.entity.HouseRuleH;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.offer_h.entity.OfferH;
import com.yun.room.domain.offer_h.service.OfferHService;
import com.yun.room.domain.room.entity.Room;
import com.yun.room.domain.room_offer_r.entity.RoomOfferR;
import com.yun.room.domain.rule_h.entity.RuleH;
import com.yun.room.domain.rule_h.service.RuleHService;
import com.yun.room.security.jwt.util.IfLogin;
import com.yun.room.security.jwt.util.LoginUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
    private final S3UploadService s3UploadService;
    private final HouseService houseService;

    private final OfferHService offerHService;
    private final RuleHService ruleHService;


    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createHouse(@IfLogin LoginUserDto loginUserDto, @RequestPart @Valid CreateHouseDto createHouseDto, @RequestPart @Valid Optional<List<MultipartFile>> files, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        log.info("location = @@@@@@@@@@@@@@@@@@@@@@@@@@@");

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        try {
            LatLng location = geocodingApiService.getLatLngFromAddress(
                    createHouseDto.getStreet() + ", "
                    + createHouseDto.getCity() + ", "
                    + createHouseDto.getCountry()
            );
            log.info("location = {}, {}", location.lat, location.lng);
            double latitude = location.lat;
            double longitude = location.lng;
            Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));

            Address address = new Address(
                    createHouseDto.getStreet()
                    , createHouseDto.getCity()
                    , createHouseDto.getCountry()
                    , createHouseDto.getPostalCode()
            );
            House house = new House(
                    createHouseDto.getTitle()
                    , createHouseDto.getDescription()
                    , createHouseDto.getType()
                    , address
                    , point
                    , createHouseDto.getRoomCount()
                    , createHouseDto.getWashroomCount()
                    , createHouseDto.getToiletCount()
                    , createHouseDto.getKitchenCount()
                    , createHouseDto.getLivingRoomCount()
            );

            // Use latitude and longitude as needed.

            log.info("images sent {}", files);

        List<Image> images = new ArrayList<>();
        try{
            if (!files.isEmpty()) {
                log.info("images sent {}", files);
                images = s3UploadService.saveFiles(files.get());
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }


        House savedHouse = houseComponentService.createHouse(house, loginUserDto.getMemberId(), images, createHouseDto.getHouseOffers(), createHouseDto.getHouseRules());

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("houseId", savedHouse.getId());


        return new ResponseEntity(resultMap, HttpStatus.OK);

        } catch (Exception e) {
            // Handle the exception
            log.error("GeocodingAPI Error : ", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{houseId}")
    public ResponseEntity updateHouse(
            @IfLogin LoginUserDto loginUserDto
            , @PathVariable(name = "houseId") Long houseId
            , @RequestPart @Valid UpdateHouseForm updateHouseForm
            , @RequestPart @Valid Optional<List<MultipartFile>> addedFiles
            , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }


        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        try {
            LatLng location = geocodingApiService.getLatLngFromAddress(
                    updateHouseForm.getStreet() + ", "
                            + updateHouseForm.getCity() + ", "
                            + updateHouseForm.getCountry()
            );
            log.info("location = {}, {}", location.lat, location.lng);
            double latitude = location.lat;
            double longitude = location.lng;
            Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));

            Address address = new Address(
                    updateHouseForm.getStreet()
                    , updateHouseForm.getCity()
                    , updateHouseForm.getCountry()
                    , updateHouseForm.getPostalCode()
            );

            List<Image> addedImages = new ArrayList<>();
            try{
                if (!addedFiles.isEmpty()) {
                    addedImages = s3UploadService.saveFiles(addedFiles.get());
                }
            } catch (Exception e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            try{
                if (!updateHouseForm.getDeletedFiles().isEmpty()) {
                    s3UploadService.deleteImages(updateHouseForm.getDeletedFiles());
                }
            } catch (Exception e) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            HouseUpdateDto houseUpdateDto = new HouseUpdateDto(
                    houseId
                    , updateHouseForm.getTitle()
                    , updateHouseForm.getDescription()
                    , updateHouseForm.getType()
                    , address
                    , point
                    , updateHouseForm.getRoomCount()
                    , updateHouseForm.getWashroomCount()
                    , updateHouseForm.getToiletCount()
                    , updateHouseForm.getKitchenCount()
                    , updateHouseForm.getLivingRoomCount()
                    , updateHouseForm.getAddedRules()
                    , updateHouseForm.getDeletedRules()
                    , updateHouseForm.getAddedOffers()
                    , updateHouseForm.getDeletedOffers()
                    , updateHouseForm.getDeletedFiles()
            );

            houseComponentService.updateHouse(addedImages, houseUpdateDto);


            return new ResponseEntity(HttpStatus.OK);

        } catch (Exception e) {
            // Handle the exception
            log.error("GeocodingAPI Error : ", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{houseId}")
    public ResponseEntity getHouse(@PathVariable Long houseId) {
        House house = houseService.findById(houseId);

        HouseResponseDto houseResponseDto = new HouseResponseDto(house);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("house", houseResponseDto);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }


    @GetMapping("/options")
    public ResponseEntity getOptions() {

        List<OfferH> offers = offerHService.findAll();
        List<OfferHDto> offerHDtos = new ArrayList<>();

        for (OfferH offer : offers) {
            offerHDtos.add(new OfferHDto(offer));
        }

        List<RuleH> rules = ruleHService.findAll();
        List<RuleHDto> ruleHDtos = new ArrayList<>();

        for (RuleH rule : rules) {
            ruleHDtos.add(new RuleHDto(rule));
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("offers", offerHDtos);
        resultMap.put("rules", ruleHDtos);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity searchHouses(SearchHousesDto searchHousesDto) {
        log.info("latitude: {}", searchHousesDto.getLat());
        log.info("availableDate: {}", searchHousesDto.getAvailableDate());

        Page<House> page = houseService.searchByDistance(searchHousesDto.getLng(), searchHousesDto.getLat(), searchHousesDto.getDistance(), searchHousesDto.getType(), searchHousesDto.getMinPrice(), searchHousesDto.getMaxPrice(), searchHousesDto.getHasBasement(), searchHousesDto.getAvailableDate(), PageRequest.of(searchHousesDto.getPageNumber(), searchHousesDto.getPageSize()));
        long totalCount = page.getTotalElements();
        long totalPage = page.getTotalPages();
        List<House> houses = page.getContent();
        List<HouseResponseDto> responseDtos = new ArrayList<>();

        //House data transfer
        for (House house : houses) {
            responseDtos.add(new HouseResponseDto(house));
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("houseList", responseDtos);
        resultMap.put("totalCount", totalCount);
        resultMap.put("totalPage", totalPage);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }
}
