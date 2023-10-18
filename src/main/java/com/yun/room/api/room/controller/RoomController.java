package com.yun.room.api.room.controller;

import com.yun.room.api.external_service.S3UploadService;

import com.yun.room.api.house.dto.get_all_houses.ImageDto;

import com.yun.room.api.house.dto.get_all_houses.RoomDto;

import com.yun.room.api.house.dto.get_options.OfferHDto;
import com.yun.room.api.house.dto.get_options.RuleHDto;
import com.yun.room.api.room.dto.create_room.CreateRoomDto;
import com.yun.room.api.room.dto.get_options.OfferRDto;
import com.yun.room.api.room.dto.get_room.HouseDto;
import com.yun.room.api.room.dto.get_room.RoomResponseDto;
import com.yun.room.domain.component_service.room.service.RoomComponentService;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house_offer_h.entity.HouseOfferH;
import com.yun.room.domain.house_rule_h.entity.HouseRuleH;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.offer_r.entity.OfferR;
import com.yun.room.domain.offer_r.service.OfferRService;
import com.yun.room.domain.room.entity.Room;
import com.yun.room.domain.room.service.RoomService;
import com.yun.room.domain.room_offer_r.entity.RoomOfferR;
import com.yun.room.security.jwt.util.IfLogin;
import com.yun.room.security.jwt.util.LoginUserDto;
import lombok.RequiredArgsConstructor;

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

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/rooms")
public class RoomController {

    private final S3UploadService s3UploadService;
    private final RoomComponentService roomComponentService;
    private final RoomService roomService;
    private final OfferRService offerRService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createRoom(@IfLogin LoginUserDto loginUserDto, @RequestPart @Valid CreateRoomDto createRoomDto, @RequestPart @Valid Optional<List<MultipartFile>> files, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        List<Image> images = new ArrayList<>();
        try{
            if (!files.isEmpty()) {
                images = s3UploadService.saveFiles(files.get());
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Room room = new Room(
                createRoomDto.getTitle()
                , createRoomDto.getDescription()
                , createRoomDto.getPrice()
                , createRoomDto.getMinStay()
                , createRoomDto.getIsOn()
                , createRoomDto.getAvailableDate()
        );

        Room savedRoom = roomComponentService.createRoom(room, createRoomDto.getHouseId(), images, createRoomDto.getRoomOffers());

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("roomId", savedRoom.getId());

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity getRoom(@PathVariable(name = "roomId") Long roomId) {
        Room room = roomService.findById(roomId);

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

        ///////
        House house = room.getHouse();
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

        //Room from house data transfer
        List<Room> rooms = house.getRooms();
        List<RoomDto> roomDtos = new ArrayList<>();
        for (Room roomFromHouse : rooms) {
            if(roomFromHouse.getId().equals(roomId)) continue; //skip the room of path variable

            List<Image> roomFromHouseImages = roomFromHouse.getImages();
            List<ImageDto> roomFromHouseImageDtos = new ArrayList<>();
            for (Image roomFromHouseImage : roomFromHouseImages) {
                roomFromHouseImageDtos.add(new ImageDto(roomFromHouseImage));
            }

            List<RoomOfferR> roomFromHouseOfferRList = roomFromHouse.getRoomOfferRList();
            List<OfferRDto> roomFromHouseOfferRDtos = new ArrayList<>();
            for (RoomOfferR roomFromHouseOfferR : roomFromHouseOfferRList) {
                roomFromHouseOfferRDtos.add(new OfferRDto(roomFromHouseOfferR.getOfferR()));
            }

            roomDtos.add(new RoomDto(
                    roomFromHouse.getId()
                    , roomFromHouse.getTitle()
                    , roomFromHouse.getDescription()
                    , roomFromHouse.getPrice()
                    , roomFromHouse.getMinStay()
                    , roomFromHouse.getIsOn()
                    , roomFromHouse.getAvailableDate()
                    , roomFromHouseImageDtos
                    , roomFromHouseOfferRDtos
            ));

        }

        HouseDto houseDto = new HouseDto(
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

        RoomResponseDto roomResponseDto = new RoomResponseDto(
                room.getId()
                , room.getTitle()
                , room.getDescription()
                , room.getPrice()
                , room.getMinStay()
                , room.getIsOn()
                , room.getAvailableDate()
                , roomImageDtos
                , roomOfferRDtos
                , houseDto
        );

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("room", roomResponseDto);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @GetMapping("/options")
    public ResponseEntity getOptions() {

        List<OfferR> offers = offerRService.findAll();
        List<OfferRDto> offerRDtos = new ArrayList<>();
        for (OfferR offer : offers) {
            offerRDtos.add(new OfferRDto(offer));
        }


        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("offers", offerRDtos);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }
}
