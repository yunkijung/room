package com.yun.room.domain.component_service.house.service;

import com.yun.room.domain.component_service.house.dto.HouseUpdateDto;
import com.yun.room.domain.component_service.room.dto.RoomUpdateDto;
import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.service.HouseService;
import com.yun.room.domain.house_offer_h.entity.HouseOfferH;
import com.yun.room.domain.house_offer_h.repository.HouseOfferHRepository;
import com.yun.room.domain.house_rule_h.entity.HouseRuleH;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.image.service.ImageService;
import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.member.service.MemberService;
import com.yun.room.domain.offer_h.entity.OfferH;
import com.yun.room.domain.offer_h.service.OfferHService;
import com.yun.room.domain.offer_r.entity.OfferR;
import com.yun.room.domain.room_offer_r.entity.RoomOfferR;
import com.yun.room.domain.rule_h.entity.RuleH;
import com.yun.room.domain.rule_h.service.RuleHService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HouseComponentService {
    private final HouseService houseService;
    private final MemberService memberService;
    private final OfferHService offerHService;
    private final RuleHService ruleHService;

    @Transactional
    public House createHouse(House house, Long hostId, List<Image> images, List<Long> houseOfferIds, List<Long> houseRuleIds) {
        List<HouseOfferH> houseOfferHList = new ArrayList<>();
        List<OfferH> offerHList = offerHService.findAllByIdIn(houseOfferIds);
        for (OfferH offerH : offerHList) {
            houseOfferHList.add(new HouseOfferH(house, offerH));
        }
        house.updateHouseOfferHList(houseOfferHList);

        List<HouseRuleH> houseRuleHList = new ArrayList<>();
        List<RuleH> ruleHList = ruleHService.findAllByIdIn(houseRuleIds);
        for (RuleH ruleH : ruleHList) {
            houseRuleHList.add(new HouseRuleH(house, ruleH));
        }
        house.updateHouseRuleHList(houseRuleHList);

        Member host = memberService.findById(hostId);
        house.updateHost(host);
        house.updateImages(images);
        return houseService.save(house);
    }

    @Transactional
    public House updateHouse(List<Image> addedImages, HouseUpdateDto houseUpdateDto) {
        House house = houseService.findById(houseUpdateDto.getHouseId());

        // remove offers
        if(houseUpdateDto.getDeletedOffers() != null) {
            List<HouseOfferH> houseOfferHList = house.getHouseOfferHList();
            List<HouseOfferH> deletedHouseOfferHList = new ArrayList<>();

            for (HouseOfferH houseOfferH : houseOfferHList) {
                if (houseUpdateDto.getDeletedOffers().contains(houseOfferH.getOfferH().getId())) {
                    deletedHouseOfferHList.add(houseOfferH);
                }
            }
            houseOfferHList.removeAll(deletedHouseOfferHList);
        }

        // add offers
        if(houseUpdateDto.getAddedOffers() != null) {
            List<OfferH> addedOfferHList = offerHService.findAllByIdIn(houseUpdateDto.getAddedOffers());
            for (OfferH offerH : addedOfferHList) {
                house.getHouseOfferHList().add(new HouseOfferH(house, offerH));
            }
        }


        // remove rules
        if(houseUpdateDto.getDeletedRules() != null) {
            List<HouseRuleH> houseRuleHList = house.getHouseRuleHList();
            List<HouseRuleH> deletedHouseRuleHList = new ArrayList<>();

            for (HouseRuleH houseRuleH : houseRuleHList) {
                if (houseUpdateDto.getDeletedRules().contains(houseRuleH.getRuleH().getId())) {
                    deletedHouseRuleHList.add(houseRuleH);
                }
            }
            houseRuleHList.removeAll(deletedHouseRuleHList);
        }

        // add rules
        if(houseUpdateDto.getAddedRules() != null) {
            List<RuleH> addedRuleHList = ruleHService.findAllByIdIn(houseUpdateDto.getAddedRules());
            for (RuleH ruleH : addedRuleHList) {
                house.getHouseRuleHList().add(new HouseRuleH(house, ruleH));
            }
        }


        // remove images
        if(houseUpdateDto.getDeletedFiles() != null) {
            List<Image> images = house.getImages();
            List<Image> deletedImages = new ArrayList<>();

            for (Image image : images) {
                if (houseUpdateDto.getDeletedFiles().contains(image.getFileUrl())) {
                    deletedImages.add(image);
                }
            }
            images.removeAll(deletedImages);
        }

        // add images
        if(addedImages != null) {
            List<Image> images = house.getImages();
            for (Image image : addedImages) {
                images.add(image);
            }
            house.updateImages(images);
        }

        // update house info
        house.updateHouseInfo(
                houseUpdateDto.getTitle()
                , houseUpdateDto.getDescription()
                , houseUpdateDto.getType()
                , houseUpdateDto.getAddress()
                , houseUpdateDto.getPoint()
                , houseUpdateDto.getRoomCount()
                , houseUpdateDto.getWashroomCount()
                , houseUpdateDto.getToiletCount()
                , houseUpdateDto.getKitchenCount()
                , houseUpdateDto.getLivingRoomCount()
        );

        return house;
    }
}
