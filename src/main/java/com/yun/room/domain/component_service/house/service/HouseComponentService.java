package com.yun.room.domain.component_service.house.service;

import com.yun.room.domain.house.entity.House;
import com.yun.room.domain.house.service.HouseService;
import com.yun.room.domain.house_offer_h.entity.HouseOfferH;
import com.yun.room.domain.house_rule_h.entity.HouseRuleH;
import com.yun.room.domain.image.entity.Image;
import com.yun.room.domain.image.service.ImageService;
import com.yun.room.domain.member.entity.Member;
import com.yun.room.domain.member.service.MemberService;
import com.yun.room.domain.offer_h.entity.OfferH;
import com.yun.room.domain.offer_h.service.OfferHService;
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
}
