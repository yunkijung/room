package com.yun.room.domain.component_service.room.service;

import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.service.HouseService;
import com.yun.room.domain.house_offer_h.entity.HouseOfferH;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.offer_h.entity.OfferH;
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
}
