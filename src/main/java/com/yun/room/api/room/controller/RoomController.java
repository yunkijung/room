package com.yun.room.api.room.controller;

import com.yun.room.api.external_service.S3UploadService;


import com.yun.room.api.room.dto.create_room.CreateRoomDto;
import com.yun.room.api.room.dto.get_inspection_reqs.BusinessInspectionReqResponseDto;
import com.yun.room.api.room.dto.get_options.OfferRDto;
import com.yun.room.api.room.dto.get_room.RoomResponseDto;
import com.yun.room.api.room.dto.update_room.UpdateRoomDto;
import com.yun.room.api.room.dto.update_room_on_off.UpdateRoomOnOffDto;
import com.yun.room.domain.component_service.room.dto.RoomUpdateDto;
import com.yun.room.domain.component_service.room.dto.RoomUpdateOnOffDto;
import com.yun.room.domain.component_service.room.service.RoomComponentService;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.inspection_req.entity.InspectionReq;
import com.yun.room.domain.inspection_req.service.InspectionReqService;
import com.yun.room.domain.offer_r.entity.OfferR;
import com.yun.room.domain.offer_r.service.OfferRService;
import com.yun.room.domain.room.entity.Room;
import com.yun.room.domain.room.service.RoomService;
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
    private final InspectionReqService inspectionReqService;

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
                , createRoomDto.getFloor()
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

    @PutMapping("/{roomId}")
    public ResponseEntity updateRoom(
            @IfLogin LoginUserDto loginUserDto
            , @PathVariable(name = "roomId") Long roomId
            , @RequestPart @Valid UpdateRoomDto updateRoomDto
            , @RequestPart @Valid Optional<List<MultipartFile>> addedFiles
            , BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // If there are validation errors, return details in the response body
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
//        if (bindingResult.hasErrors()) {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }

        List<Image> addedImages = new ArrayList<>();
        try{
            if (!addedFiles.isEmpty()) {
                addedImages = s3UploadService.saveFiles(addedFiles.get());
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try{
            if (!updateRoomDto.getDeletedFiles().isEmpty()) {
                s3UploadService.deleteImages(updateRoomDto.getDeletedFiles());
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        RoomUpdateDto roomUpdateDto = new RoomUpdateDto(
                roomId
                , updateRoomDto.getTitle()
                , updateRoomDto.getDescription()
                , updateRoomDto.getFloor()
                , updateRoomDto.getPrice()
                , updateRoomDto.getMinStay()
                , updateRoomDto.getAvailableDate()
                , updateRoomDto.getAddedOffers()
                , updateRoomDto.getDeletedOffers()
                , updateRoomDto.getDeletedFiles()
        );

        roomComponentService.updateRoom(addedImages, roomUpdateDto);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity getRoom(@PathVariable(name = "roomId") Long roomId) {
        Room room = roomService.findById(roomId);

        RoomResponseDto roomResponseDto = new RoomResponseDto(room);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("room", roomResponseDto);
        return new ResponseEntity(resultMap, HttpStatus.OK);
    }

    @PutMapping("/{roomId}/on-off")
    public ResponseEntity updateRoomOnOff(@PathVariable(name = "roomId") Long roomId, @RequestBody @Valid UpdateRoomOnOffDto updateRoomOnOffDto, BindingResult bindingResult) {

        Room room = roomComponentService.updateRoomOnOff(roomId, new RoomUpdateOnOffDto(updateRoomOnOffDto.getIsOn()));

        return new ResponseEntity(HttpStatus.OK);
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

    @GetMapping("/{roomId}/inspection-req")
    public ResponseEntity getInspectionReqs(@PathVariable(name = "roomId") Long roomId) {

        List<InspectionReq> inspectionReqs = inspectionReqService.findByRoomId(roomId);

        List<BusinessInspectionReqResponseDto> inspectionReqResponseDtos = new ArrayList<>();

        for (InspectionReq inspectionReq : inspectionReqs) {
            inspectionReqResponseDtos.add(new BusinessInspectionReqResponseDto(inspectionReq));
        }
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("inspectionReqList", inspectionReqResponseDtos);

        return new ResponseEntity(resultMap, HttpStatus.OK);
    }
}
