package com.yun.room.api.house.controller;

import com.google.maps.model.LatLng;
import com.yun.room.api.external_service.S3UploadService;
import com.yun.room.api.external_service.S3Uploader;
import com.yun.room.api.house.dto.create_house.CreateHouseDto;
import com.yun.room.api.house.dto.get_all_houses.*;
import com.yun.room.api.external_service.GeocodingAPIService;
import com.yun.room.api.house.dto.get_options.OfferHDto;
import com.yun.room.api.house.dto.get_options.RuleHDto;
import com.yun.room.api.house.dto.search_houses.SearchHousesDto;
import com.yun.room.api.room.dto.get_options.OfferRDto;
import com.yun.room.domain.common.embedded.Address;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
    private final S3Uploader s3Uploader;
    private final S3UploadService s3UploadService;
    private final HouseService houseService;

    private final OfferHService offerHService;
    private final RuleHService ruleHService;

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
                    , address
                    , point
                    , createHouseDto.getRoomCount()
                    , createHouseDto.getWashroomCount()
                    , createHouseDto.getToiletCount()
                    , createHouseDto.getKitchenCount()
                    , createHouseDto.getLivingRoomCount()
            );

            // Use latitude and longitude as needed.




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

    @GetMapping
    public ResponseEntity getAllHouses() {
        List<House> allHouses = houseService.findAll();
        List<HouseResponseDto> responseDtos = new ArrayList<>();

        //House data transfer
        for (House house : allHouses) {
            List<Image> images = house.getImages();
            List<ImageDto> imageDtos = new ArrayList<>();
            for (Image image : images) {
                imageDtos.add(new ImageDto(image));
            }

            List<HouseOfferH> houseOfferHList = house.getHouseOfferHList();
            List<OfferHDto> offerHDtos = new ArrayList<>();
            for (HouseOfferH houseOfferH : houseOfferHList) {
                offerHDtos.add(new OfferHDto(houseOfferH.getOfferH()));
            }

            List<HouseRuleH> houseRuleHList = house.getHouseRuleHList();
            List<RuleHDto> ruleHDtos = new ArrayList<>();
            for (HouseRuleH houseRuleH : houseRuleHList) {
                ruleHDtos.add(new RuleHDto(houseRuleH.getRuleH()));
            }

            //Room data transfer
            List<Room> rooms = house.getRooms();
            List<RoomDto> roomDtos = new ArrayList<>();
            for (Room room : rooms) {
                List<Image> roomImages = room.getImages();
                List<ImageDto> roomImageDtos = new ArrayList<>();
                for (Image roomImage : roomImages) {
                    roomImageDtos.add(new ImageDto(roomImage));
                }

                List<RoomOfferR> roomOfferRList = room.getRoomOfferRList();
                List<OfferRDto> roomOfferRDtos = new ArrayList<>();
                for (RoomOfferR roomOfferR : roomOfferRList) {
                    roomOfferRDtos.add(new OfferRDto(roomOfferR.getOfferR()));
                }

                roomDtos.add(new RoomDto(
                        room.getId()
                        , room.getTitle()
                        , room.getDescription()
                        , room.getPrice()
                        , room.getMinStay()
                        , room.getIsOn()
                        , room.getAvailableDate()
                        , roomImageDtos
                        , roomOfferRDtos
                ));

            }


            responseDtos.add(new HouseResponseDto(
                    house.getId()
                    , house.getTitle()
                    , house.getDescription()
                    , house.getAddress().getStreet()
                    , house.getAddress().getCity()
                    , house.getAddress().getCountry()
                    , house.getAddress().getPostalCode()
                    , house.getRoomCount()
                    , house.getWashroomCount()
                    , house.getToiletCount()
                    , house.getKitchenCount()
                    , house.getLivingRoomCount()
                    , house.getPoint().getX()
                    , house.getPoint().getY()
                    , imageDtos
                    , offerHDtos
                    , ruleHDtos
                    , roomDtos
            ));
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("houseList", responseDtos);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @GetMapping("/{houseId}")
    public ResponseEntity getHouse(@PathVariable Long houseId) {
        House house = houseService.findById(houseId);

        List<Image> images = house.getImages();
        List<ImageDto> imageDtos = new ArrayList<>();
        for (Image image : images) {
            imageDtos.add(new ImageDto(image));
        }

        List<HouseOfferH> houseOfferHList = house.getHouseOfferHList();
        List<OfferHDto> offerHDtos = new ArrayList<>();
        for (HouseOfferH houseOfferH : houseOfferHList) {
            offerHDtos.add(new OfferHDto(houseOfferH.getOfferH()));
        }

        List<HouseRuleH> houseRuleHList = house.getHouseRuleHList();
        List<RuleHDto> ruleHDtos = new ArrayList<>();
        for (HouseRuleH houseRuleH : houseRuleHList) {
            ruleHDtos.add(new RuleHDto(houseRuleH.getRuleH()));
        }

        //Room data transfer
        List<Room> rooms = house.getRooms();
        List<RoomDto> roomDtos = new ArrayList<>();
        for (Room room : rooms) {
            List<Image> roomImages = room.getImages();
            List<ImageDto> roomImageDtos = new ArrayList<>();
            for (Image roomImage : roomImages) {
                roomImageDtos.add(new ImageDto(roomImage));
            }

            List<RoomOfferR> roomOfferRList = room.getRoomOfferRList();
            List<OfferRDto> roomOfferRDtos = new ArrayList<>();
            for (RoomOfferR roomOfferR : roomOfferRList) {
                roomOfferRDtos.add(new OfferRDto(roomOfferR.getOfferR()));
            }

            roomDtos.add(new RoomDto(
                    room.getId()
                    , room.getTitle()
                    , room.getDescription()
                    , room.getPrice()
                    , room.getMinStay()
                    , room.getIsOn()
                    , room.getAvailableDate()
                    , roomImageDtos
                    , roomOfferRDtos
            ));

        }

        HouseResponseDto houseResponseDto = new HouseResponseDto(
                house.getId()
                , house.getTitle()
                , house.getDescription()
                , house.getAddress().getStreet()
                , house.getAddress().getCity()
                , house.getAddress().getCountry()
                , house.getAddress().getPostalCode()
                , house.getRoomCount()
                , house.getWashroomCount()
                , house.getToiletCount()
                , house.getKitchenCount()
                , house.getLivingRoomCount()
                , house.getPoint().getX()
                , house.getPoint().getY()
                , imageDtos
                , offerHDtos
                , ruleHDtos
                , roomDtos
        );

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
        List<House> houses = houseService.searchByDistance(searchHousesDto.getLng(), searchHousesDto.getLat(), searchHousesDto.getDistance());
        List<HouseResponseDto> responseDtos = new ArrayList<>();

        //House data transfer
        for (House house : houses) {
            List<Image> images = house.getImages();
            List<ImageDto> imageDtos = new ArrayList<>();
            for (Image image : images) {
                imageDtos.add(new ImageDto(image));
            }

            List<HouseOfferH> houseOfferHList = house.getHouseOfferHList();
            List<OfferHDto> offerHDtos = new ArrayList<>();
            for (HouseOfferH houseOfferH : houseOfferHList) {
                offerHDtos.add(new OfferHDto(houseOfferH.getOfferH()));
            }

            List<HouseRuleH> houseRuleHList = house.getHouseRuleHList();
            List<RuleHDto> ruleHDtos = new ArrayList<>();
            for (HouseRuleH houseRuleH : houseRuleHList) {
                ruleHDtos.add(new RuleHDto(houseRuleH.getRuleH()));
            }

            //Room data transfer
            List<Room> rooms = house.getRooms();
            List<RoomDto> roomDtos = new ArrayList<>();
            for (Room room : rooms) {
                List<Image> roomImages = room.getImages();
                List<ImageDto> roomImageDtos = new ArrayList<>();
                for (Image roomImage : roomImages) {
                    roomImageDtos.add(new ImageDto(roomImage));
                }

                List<RoomOfferR> roomOfferRList = room.getRoomOfferRList();
                List<OfferRDto> roomOfferRDtos = new ArrayList<>();
                for (RoomOfferR roomOfferR : roomOfferRList) {
                    roomOfferRDtos.add(new OfferRDto(roomOfferR.getOfferR()));
                }

                roomDtos.add(new RoomDto(
                        room.getId()
                        , room.getTitle()
                        , room.getDescription()
                        , room.getPrice()
                        , room.getMinStay()
                        , room.getIsOn()
                        , room.getAvailableDate()
                        , roomImageDtos
                        , roomOfferRDtos
                ));

            }


            responseDtos.add(new HouseResponseDto(
                    house.getId()
                    , house.getTitle()
                    , house.getDescription()
                    , house.getAddress().getStreet()
                    , house.getAddress().getCity()
                    , house.getAddress().getCountry()
                    , house.getAddress().getPostalCode()
                    , house.getRoomCount()
                    , house.getWashroomCount()
                    , house.getToiletCount()
                    , house.getKitchenCount()
                    , house.getLivingRoomCount()
                    , house.getPoint().getX()
                    , house.getPoint().getY()
                    , imageDtos
                    , offerHDtos
                    , ruleHDtos
                    , roomDtos
            ));
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("houseList", responseDtos);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }
}
