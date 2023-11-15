package com.yun.room.domain.component_service.room.service;

import com.yun.room.domain.component_service.room.dto.RoomUpdateDto;
import com.yun.room.domain.component_service.room.dto.RoomUpdateOnOffDto;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.service.HouseService;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.offer_r.entity.OfferR;
import com.yun.room.domain.offer_r.service.OfferRService;
import com.yun.room.domain.room.entity.Room;
import com.yun.room.domain.room.service.RoomService;
import com.yun.room.domain.room_offer_r.entity.RoomOfferR;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomComponentService {
    private final RoomService roomService;
    private final HouseService houseService;
    private final OfferRService offerRService;
    @Transactional
    public Room createRoom(Room room, Long houseId, List<Image> images, List<Long> roomOfferIds) {
        List<RoomOfferR> roomOfferRList = new ArrayList<>();
        List<OfferR> offerRList = offerRService.findAllByIdIn(roomOfferIds);
        for (OfferR offerR : offerRList) {
            roomOfferRList.add(new RoomOfferR(room, offerR));
        }

        House house = houseService.findById(houseId);

        room.updateRoomOfferRList(roomOfferRList);
        room.updateHouse(house);
        room.updateImages(images);
        return roomService.save(room);
    }

    @Transactional
    public Room updateRoom(List<Image> addedImages, RoomUpdateDto roomUpdateDto) {
        Room room = roomService.findById(roomUpdateDto.getRoomId());

        // remove offers
        if(roomUpdateDto.getDeletedOffers() != null) {
            List<RoomOfferR> roomOfferRList = room.getRoomOfferRList();
            List<RoomOfferR> deletedRoomOfferRList = new ArrayList<>();

            for (RoomOfferR roomOfferR : roomOfferRList) {
                if (roomUpdateDto.getDeletedOffers().contains(roomOfferR.getOfferR().getId())) {
                    deletedRoomOfferRList.add(roomOfferR);
                }
            }
            roomOfferRList.removeAll(deletedRoomOfferRList);
        }



        // add offers
        if(roomUpdateDto.getAddedOffers() != null) {
            List<OfferR> addedOfferRList = offerRService.findAllByIdIn(roomUpdateDto.getAddedOffers());
            for (OfferR offerR : addedOfferRList) {
                room.getRoomOfferRList().add(new RoomOfferR(room, offerR));
            }
        }




        // remove images
        if(roomUpdateDto.getDeletedFiles() != null) {
            List<Image> images = room.getImages();
            List<Image> deletedImages = new ArrayList<>();

            for (Image image : images) {
                if (roomUpdateDto.getDeletedFiles().contains(image.getFileUrl())) {
                    deletedImages.add(image);
                }
            }
            images.removeAll(deletedImages);
        }



        // add images
        if(addedImages != null) {
            for (Image image : addedImages) {
                room.getImages().add(image);
            }
        }

        room.updateRoomInfo(
                roomUpdateDto.getTitle()
                , roomUpdateDto.getDescription()
                , roomUpdateDto.getFloor()
                , roomUpdateDto.getPrice()
                , roomUpdateDto.getMinStay()
                , roomUpdateDto.getAvailableDate()
        );


        return room;
    }

    @Transactional
    public Room updateRoomOnOff(Long roomId, RoomUpdateOnOffDto roomUpdateOnOffDto) {
        Room room = roomService.findById(roomId);

        if(roomUpdateOnOffDto.getIsOn() != null) {
            room.updateIsOn(roomUpdateOnOffDto.getIsOn());
        }

        return room;
    }
}
