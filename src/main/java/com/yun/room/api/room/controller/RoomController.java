package com.yun.room.api.room.controller;

import com.yun.room.api.external_service.S3UploadService;
import com.yun.room.api.house.dto.create_house.CreateHouseDto;
import com.yun.room.api.room.dto.create_room.CreateRoomDto;
import com.yun.room.domain.component_service.room.service.RoomComponentService;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.room.entity.Room;
import com.yun.room.security.jwt.util.IfLogin;
import com.yun.room.security.jwt.util.LoginUserDto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/rooms")
public class RoomController {

    private final S3UploadService s3UploadService;
    private final RoomComponentService roomComponentService;

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

        return new ResponseEntity(savedRoom.getId(), HttpStatus.OK);
    }
}
